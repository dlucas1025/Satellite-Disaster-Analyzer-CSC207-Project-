package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

import entity.Community;
import use_case.shared.DataUnavailableException;

/**
 * Tests place search without the internet: parsing uses saved responses in
 * src/test/resources/geocoding, and the HTTP tests use a tiny local server. Owner: Shreyas.
 */
class GeocodingDataAccessObjectTest {

    private static final int HTTP_OK = 200;
    private static final int HTTP_SERVER_ERROR = 500;

    private static String resource(String name) throws IOException {
        try (InputStream in = GeocodingDataAccessObjectTest.class.getResourceAsStream("/geocoding/" + name)) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // ---------------- parsing ----------------

    @Test
    void parseKeepsOnlyCanadianPlacesInOrder() throws IOException {
        final List<Community> places = GeocodingDataAccessObject.parse(resource("peguis.json"));
        assertEquals(2, places.size());
        assertEquals("Peguis", places.get(0).name());
        assertEquals("Peguis", places.get(1).name());
    }

    @Test
    void parseConvertsProvinceNamesToCodes() throws IOException {
        final Community first = GeocodingDataAccessObject.parse(resource("peguis.json")).get(0);
        assertEquals("MB", first.province());
    }

    @Test
    void parseGivesEachPlaceAStableUniqueId() throws IOException {
        final List<Community> places = GeocodingDataAccessObject.parse(resource("peguis.json"));
        assertEquals("geo-6100832", places.get(0).id());
        assertTrue(!places.get(0).id().equals(places.get(1).id()));
    }

    @Test
    void parseReadsCoordinates() throws IOException {
        final Community first = GeocodingDataAccessObject.parse(resource("peguis.json")).get(0);
        assertEquals(50.08337, first.latitude(), 1e-6);
        assertEquals(-96.95028, first.longitude(), 1e-6);
    }

    @Test
    void parseWithNoResultsGivesEmptyList() throws IOException {
        assertTrue(GeocodingDataAccessObject.parse(resource("no_results.json")).isEmpty());
    }

    @Test
    void parseRejectsGarbage() {
        assertThrows(DataUnavailableException.class, () -> GeocodingDataAccessObject.parse("<html>oops</html>"));
    }

    // ---------------- HTTP, against a local server ----------------

    @Test
    void searchCallsTheServiceAndParsesTheAnswer() throws IOException {
        final AtomicReference<String> seenQuery = new AtomicReference<>();
        final HttpServer server = startServer(HTTP_OK, resource("peguis.json"), seenQuery);
        try {
            final List<Community> places = searchVia(server).searchPlaces("St. Andrews & Peguis");
            assertEquals(2, places.size());
            assertTrue(seenQuery.get().contains("countryCode=CA"));
            assertTrue(seenQuery.get().contains("name=St.+Andrews+%26+Peguis"));
        }
        finally {
            server.stop(0);
        }
    }

    @Test
    void serverErrorGivesDataUnavailable() throws IOException {
        final HttpServer server = startServer(HTTP_SERVER_ERROR, "{}", new AtomicReference<>());
        try {
            final GeocodingDataAccessObject search = searchVia(server);
            assertThrows(DataUnavailableException.class, () -> search.searchPlaces("Peguis"));
        }
        finally {
            server.stop(0);
        }
    }

    @Test
    void unreachableServiceGivesDataUnavailable() {
        final GeocodingDataAccessObject search =
                new GeocodingDataAccessObject(HttpClient.newHttpClient(), "http://localhost:1/v1/search");
        assertThrows(DataUnavailableException.class, () -> search.searchPlaces("Peguis"));
    }

    private static GeocodingDataAccessObject searchVia(HttpServer server) {
        final String url = "http://localhost:" + server.getAddress().getPort() + "/v1/search";
        return new GeocodingDataAccessObject(HttpClient.newHttpClient(), url);
    }

    private static HttpServer startServer(int status, String body, AtomicReference<String> seenQuery)
            throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/v1/search", exchange -> {
            seenQuery.set(exchange.getRequestURI().getRawQuery());
            final byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });
        server.start();
        return server;
    }
}
