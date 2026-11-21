package data_access;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Community;
import use_case.save_community.PlaceSearchDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Searches Canadian places with the Open-Meteo Geocoding API (no key needed). Owner: Shreyas.
 *
 * <p>Fetching and parsing are split: {@link #searchPlaces} does the HTTP call and
 * {@link #parse} turns the JSON into communities, so parsing can be tested on saved
 * responses without the internet.</p>
 */
public class GeocodingDataAccessObject implements PlaceSearchDataAccessInterface {

    /** The real service. */
    public static final String DEFAULT_BASE_URL = "https://geocoding-api.open-meteo.com/v1/search";

    private static final int MAX_RESULTS = 10;
    private static final int HTTP_OK = 200;
    private static final Duration TIMEOUT = Duration.ofSeconds(15);
    private static final String CANADA = "CA";

    private static final Map<String, String> PROVINCE_CODES = Map.ofEntries(
            Map.entry("Alberta", "AB"),
            Map.entry("British Columbia", "BC"),
            Map.entry("Manitoba", "MB"),
            Map.entry("New Brunswick", "NB"),
            Map.entry("Newfoundland and Labrador", "NL"),
            Map.entry("Nova Scotia", "NS"),
            Map.entry("Northwest Territories", "NT"),
            Map.entry("Nunavut", "NU"),
            Map.entry("Ontario", "ON"),
            Map.entry("Prince Edward Island", "PE"),
            Map.entry("Quebec", "QC"),
            Map.entry("Saskatchewan", "SK"),
            Map.entry("Yukon", "YT"));

    private final HttpClient client;
    private final String baseUrl;

    /** Creates a search that uses the real Open-Meteo service. */
    public GeocodingDataAccessObject() {
        this(HttpClient.newBuilder().connectTimeout(TIMEOUT).build(), DEFAULT_BASE_URL);
    }

    /**
     * Creates a search against any server with the same API, e.g. a local test server.
     *
     * @param client  the HTTP client
     * @param baseUrl the search endpoint
     */
    public GeocodingDataAccessObject(HttpClient client, String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Community> searchPlaces(String query) {
        final String url = baseUrl
                + "?name=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&count=" + MAX_RESULTS
                + "&language=en&format=json&countryCode=" + CANADA;
        final HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(TIMEOUT).GET().build();
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HTTP_OK) {
                throw new DataUnavailableException("place search returned HTTP " + response.statusCode());
            }
            return parse(response.body());
        }
        catch (IOException ex) {
            throw new DataUnavailableException("cannot reach the place search service", ex);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new DataUnavailableException("place search was interrupted", ex);
        }
    }

    /**
     * Turns a geocoding response into communities, keeping only Canadian places.
     *
     * @param json the response body
     * @return matching places in the service's order (best first); empty if none
     * @throws DataUnavailableException if the response is not in the expected format
     */
    public static List<Community> parse(String json) {
        try {
            final JSONObject root = new JSONObject(json);
            final List<Community> places = new ArrayList<>();
            final JSONArray results = root.optJSONArray("results");
            if (results == null) {
                // The service leaves "results" out entirely when nothing matches.
                return places;
            }
            for (int i = 0; i < results.length(); i++) {
                final JSONObject item = results.getJSONObject(i);
                if (!CANADA.equals(item.optString("country_code"))) {
                    continue;
                }
                final String provinceName = item.optString("admin1", "");
                places.add(new Community(
                        "geo-" + item.getLong("id"),
                        item.getString("name"),
                        PROVINCE_CODES.getOrDefault(provinceName, provinceName),
                        item.getDouble("latitude"),
                        item.getDouble("longitude")));
            }
            return places;
        }
        catch (JSONException | IllegalArgumentException ex) {
            throw new DataUnavailableException("place search sent an unexpected response", ex);
        }
    }
}
