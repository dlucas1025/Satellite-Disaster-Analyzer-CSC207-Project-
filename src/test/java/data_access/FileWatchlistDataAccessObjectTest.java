package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Community;
import entity.Watchlist;
import use_case.shared.DataUnavailableException;

/**
 * Tests the JSON file store against real files in a temporary folder. Owner: Shreyas.
 */
class FileWatchlistDataAccessObjectTest {

    private final Community peguis = new Community("peguis-fn-mb", "Peguis First Nation", "MB", 51.33, -97.57);
    private final Community jasper = new Community("jasper-ab", "Jasper", "AB", 52.87, -118.08);

    private Path newFile() throws IOException {
        return Files.createTempDirectory("northwatch-test").resolve("sub").resolve("watchlist.json");
    }

    @Test
    void missingFileGivesEmptyWatchlist() throws IOException {
        final FileWatchlistDataAccessObject store = new FileWatchlistDataAccessObject(newFile());
        final Watchlist loaded = store.load("shreyas");
        assertEquals("shreyas", loaded.getOwner());
        assertTrue(loaded.getCommunities().isEmpty());
    }

    @Test
    void savedWatchlistSurvivesARestart() throws IOException {
        final Path file = newFile();
        final Watchlist watchlist = new Watchlist("shreyas");
        watchlist.add(peguis);
        watchlist.add(jasper);
        new FileWatchlistDataAccessObject(file).save(watchlist);

        // A brand-new store object, like reopening the app.
        final Watchlist loaded = new FileWatchlistDataAccessObject(file).load("shreyas");
        assertEquals(List.of(peguis, jasper), loaded.getCommunities());
    }

    @Test
    void saveCreatesMissingFolders() throws IOException {
        final Path file = newFile();
        new FileWatchlistDataAccessObject(file).save(new Watchlist("shreyas"));
        assertTrue(Files.exists(file));
    }

    @Test
    void usersAreKeptSeparate() throws IOException {
        final Path file = newFile();
        final FileWatchlistDataAccessObject store = new FileWatchlistDataAccessObject(file);
        final Watchlist mine = new Watchlist("shreyas");
        mine.add(peguis);
        final Watchlist theirs = new Watchlist("lucas");
        theirs.add(jasper);
        store.save(mine);
        store.save(theirs);

        assertEquals(List.of(peguis), store.load("shreyas").getCommunities());
        assertEquals(List.of(jasper), store.load("lucas").getCommunities());
    }

    @Test
    void savingAgainReplacesTheOldList() throws IOException {
        final FileWatchlistDataAccessObject store = new FileWatchlistDataAccessObject(newFile());
        final Watchlist watchlist = new Watchlist("shreyas");
        watchlist.add(peguis);
        store.save(watchlist);
        watchlist.remove("peguis-fn-mb");
        watchlist.add(jasper);
        store.save(watchlist);

        assertEquals(List.of(jasper), store.load("shreyas").getCommunities());
    }

    @Test
    void renamedNameIsSaved() throws IOException {
        final FileWatchlistDataAccessObject store = new FileWatchlistDataAccessObject(newFile());
        final Watchlist watchlist = new Watchlist("shreyas");
        watchlist.add(peguis);
        watchlist.rename("peguis-fn-mb", "Peguis FN");
        store.save(watchlist);

        assertEquals("Peguis FN", store.load("shreyas").getCommunities().get(0).name());
    }

    @Test
    void damagedFileGivesDataUnavailable() throws IOException {
        final Path file = newFile();
        Files.createDirectories(file.getParent());
        Files.writeString(file, "{ this is not json");
        final FileWatchlistDataAccessObject store = new FileWatchlistDataAccessObject(file);

        assertThrows(DataUnavailableException.class, () -> store.load("shreyas"));
    }

    @Test
    void fileIsReadableJson() throws IOException {
        final Path file = newFile();
        final Watchlist watchlist = new Watchlist("shreyas");
        watchlist.add(peguis);
        new FileWatchlistDataAccessObject(file).save(watchlist);

        final String text = Files.readString(file);
        assertTrue(text.contains("\"watchlists\""));
        assertTrue(text.contains("\"Peguis First Nation\""));
    }
}
