package use_case.save_community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import entity.Community;
import entity.Watchlist;
import use_case.shared.DataUnavailableException;

/**
 * Interactor tests for story 1. Owner: Shreyas. No network, files or GUI: everything is faked.
 */
class SaveCommunityInteractorTest {

    private static final String OWNER = "shreyas";

    private final Community peguisFn = new Community("peguis-fn-mb", "Peguis First Nation", "MB", 51.33, -97.57);
    private final Community peguisStAndrews = new Community("peguis-st-andrews-mb", "Peguis", "MB", 50.10, -96.98);
    private final Community jasper = new Community("jasper-ab", "Jasper", "AB", 52.87, -118.08);

    private final InMemoryWatchlistStore store = new InMemoryWatchlistStore();
    private final FakePlaceSearch search = new FakePlaceSearch();
    private final RecordingPresenter presenter = new RecordingPresenter();
    private final SaveCommunityInteractor interactor = new SaveCommunityInteractor(search, store, presenter);

    // ---------------- load ----------------

    @Test
    void loadShowsEmptyWatchlistForNewUser() {
        interactor.load(OWNER);
        assertTrue(presenter.lastWatchlist.watchlist().isEmpty());
        assertNull(presenter.lastError);
    }

    @Test
    void loadFailureShowsError() {
        store.failLoad = true;
        interactor.load(OWNER);
        assertTrue(presenter.lastError.startsWith("Could not load"));
    }

    // ---------------- search ----------------

    @Test
    void searchReturnsAllMatchesSoUserCanPickTheRightOne() {
        search.results = List.of(peguisStAndrews, peguisFn);
        interactor.search(SaveCommunityInputData.forSearch(OWNER, "  Peguis "));
        assertEquals("Peguis", search.lastQuery);
        assertEquals(List.of(peguisStAndrews, peguisFn), presenter.lastSearch.searchResults());
    }

    @Test
    void searchWithNoMatchesSaysSo() {
        interactor.search(SaveCommunityInputData.forSearch(OWNER, "Atlantis"));
        assertTrue(presenter.lastSearch.searchResults().isEmpty());
        assertTrue(presenter.lastSearch.message().contains("No places found"));
    }

    @Test
    void blankSearchIsRejectedWithoutCallingTheService() {
        interactor.search(SaveCommunityInputData.forSearch(OWNER, "   "));
        assertNull(search.lastQuery);
        assertTrue(presenter.lastError.contains("Type a community name"));
    }

    @Test
    void searchFailureShowsErrorAndKeepsWatchlist() {
        store.put(OWNER, peguisFn);
        search.fail = true;
        interactor.search(SaveCommunityInputData.forSearch(OWNER, "Jasper"));
        assertTrue(presenter.lastError.startsWith("Search is unavailable"));
        assertEquals(1, store.load(OWNER).getCommunities().size());
    }

    // ---------------- save ----------------

    @Test
    void saveAddsCommunityAndPersistsIt() {
        interactor.save(SaveCommunityInputData.forSave(OWNER, peguisFn));
        assertEquals(List.of(peguisFn), presenter.lastWatchlist.watchlist());
        assertEquals(List.of(peguisFn), store.load(OWNER).getCommunities());
        assertEquals(1, store.saveCount);
    }

    @Test
    void savingTwiceDoesNotDuplicate() {
        interactor.save(SaveCommunityInputData.forSave(OWNER, peguisFn));
        interactor.save(SaveCommunityInputData.forSave(OWNER, peguisFn));
        assertEquals(1, store.load(OWNER).getCommunities().size());
        assertTrue(presenter.lastWatchlist.message().contains("already on your watchlist"));
        assertEquals(1, store.saveCount);
    }

    @Test
    void saveWithoutPlaceIsRejected() {
        interactor.save(SaveCommunityInputData.forSave(OWNER, null));
        assertTrue(presenter.lastError.contains("Pick a place"));
    }

    @Test
    void saveFailureShowsError() {
        store.failSave = true;
        interactor.save(SaveCommunityInputData.forSave(OWNER, peguisFn));
        assertTrue(presenter.lastError.startsWith("Could not save"));
    }

    // ---------------- remove ----------------

    @Test
    void removeDeletesCommunityAndPersists() {
        store.put(OWNER, peguisFn, jasper);
        interactor.remove(SaveCommunityInputData.forRemove(OWNER, "peguis-fn-mb"));
        assertEquals(List.of(jasper), store.load(OWNER).getCommunities());
        assertTrue(presenter.lastWatchlist.message().contains("Removed Peguis First Nation"));
    }

    @Test
    void removeUnknownCommunityChangesNothing() {
        store.put(OWNER, jasper);
        interactor.remove(SaveCommunityInputData.forRemove(OWNER, "nowhere"));
        assertEquals(0, store.saveCount);
        assertTrue(presenter.lastWatchlist.message().contains("not on your watchlist"));
    }

    // ---------------- rename ----------------

    @Test
    void renameChangesNameAndPersists() {
        store.put(OWNER, peguisFn);
        interactor.rename(SaveCommunityInputData.forRename(OWNER, "peguis-fn-mb", "Peguis FN"));
        assertEquals("Peguis FN", store.load(OWNER).getCommunities().get(0).name());
    }

    @Test
    void renameToBlankIsRejected() {
        store.put(OWNER, peguisFn);
        interactor.rename(SaveCommunityInputData.forRename(OWNER, "peguis-fn-mb", " "));
        assertTrue(presenter.lastError.contains("Type a new name"));
        assertEquals(0, store.saveCount);
    }

    // ================= fakes =================

    /** Stores watchlists in a map; hands out copies, like a real file or server would. */
    private static final class InMemoryWatchlistStore implements WatchlistDataAccessInterface {
        private final Map<String, List<Community>> saved = new HashMap<>();
        private boolean failLoad;
        private boolean failSave;
        private int saveCount;

        void put(String owner, Community... communities) {
            saved.put(owner, new ArrayList<>(List.of(communities)));
        }

        @Override
        public Watchlist load(String owner) {
            if (failLoad) {
                throw new DataUnavailableException("disk error");
            }
            final Watchlist copy = new Watchlist(owner);
            saved.getOrDefault(owner, List.of()).forEach(copy::add);
            return copy;
        }

        @Override
        public void save(Watchlist watchlist) {
            if (failSave) {
                throw new DataUnavailableException("disk full");
            }
            saveCount++;
            saved.put(watchlist.getOwner(), new ArrayList<>(watchlist.getCommunities()));
        }
    }

    /** Returns fixed results, or fails on demand. */
    private static final class FakePlaceSearch implements PlaceSearchDataAccessInterface {
        private List<Community> results = List.of();
        private boolean fail;
        private String lastQuery;

        @Override
        public List<Community> searchPlaces(String query) {
            lastQuery = query;
            if (fail) {
                throw new DataUnavailableException("no internet");
            }
            return results;
        }
    }

    /** Remembers the last thing it was told. */
    private static final class RecordingPresenter implements SaveCommunityOutputBoundary {
        private SaveCommunityOutputData lastSearch;
        private SaveCommunityOutputData lastWatchlist;
        private String lastError;

        @Override
        public void prepareSearchResults(SaveCommunityOutputData output) {
            lastSearch = output;
        }

        @Override
        public void prepareWatchlistView(SaveCommunityOutputData output) {
            lastWatchlist = output;
        }

        @Override
        public void prepareFailView(String error) {
            lastError = error;
        }
    }
}
