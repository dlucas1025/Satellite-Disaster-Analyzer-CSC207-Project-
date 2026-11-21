package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class WatchlistTest {

    private final Community peguis = new Community("peguis-fn-mb", "Peguis First Nation", "MB", 51.33, -97.57);
    private final Community jasper = new Community("jasper-ab", "Jasper", "AB", 52.87, -118.08);
    private final Watchlist watchlist = new Watchlist("shreyas");

    @Test
    void newWatchlistIsEmpty() {
        assertTrue(watchlist.getCommunities().isEmpty());
        assertEquals("shreyas", watchlist.getOwner());
    }

    @Test
    void addKeepsOrder() {
        assertTrue(watchlist.add(peguis));
        assertTrue(watchlist.add(jasper));
        assertEquals(2, watchlist.getCommunities().size());
        assertEquals(peguis, watchlist.getCommunities().get(0));
        assertEquals(jasper, watchlist.getCommunities().get(1));
    }

    @Test
    void addingSameIdTwiceIsRejected() {
        watchlist.add(peguis);
        final Community sameIdOtherName = new Community("peguis-fn-mb", "Peguis", "MB", 51.33, -97.57);
        assertFalse(watchlist.add(sameIdOtherName));
        assertEquals(1, watchlist.getCommunities().size());
    }

    @Test
    void removeDeletesOnlyThatCommunity() {
        watchlist.add(peguis);
        watchlist.add(jasper);
        assertTrue(watchlist.remove("peguis-fn-mb"));
        assertEquals(1, watchlist.getCommunities().size());
        assertTrue(watchlist.find("jasper-ab").isPresent());
    }

    @Test
    void removeUnknownIdReturnsFalse() {
        watchlist.add(peguis);
        assertFalse(watchlist.remove("nowhere"));
        assertEquals(1, watchlist.getCommunities().size());
    }

    @Test
    void renameChangesNameButKeepsIdPositionAndLocation() {
        watchlist.add(peguis);
        watchlist.add(jasper);
        assertTrue(watchlist.rename("peguis-fn-mb", "  Peguis FN "));
        final Community renamed = watchlist.getCommunities().get(0);
        assertEquals("Peguis FN", renamed.name());
        assertEquals("peguis-fn-mb", renamed.id());
        assertEquals(51.33, renamed.latitude(), 1e-9);
    }

    @Test
    void renameUnknownIdReturnsFalse() {
        assertFalse(watchlist.rename("nowhere", "X"));
    }

    @Test
    void renameToBlankIsRejected() {
        watchlist.add(peguis);
        assertThrows(IllegalArgumentException.class, () -> watchlist.rename("peguis-fn-mb", " "));
    }

    @Test
    void findReturnsEmptyWhenMissing() {
        assertFalse(watchlist.find("nowhere").isPresent());
    }

    @Test
    void returnedListCannotBeModified() {
        watchlist.add(peguis);
        assertThrows(UnsupportedOperationException.class, () -> watchlist.getCommunities().add(jasper));
    }

    @Test
    void blankOwnerIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Watchlist(" "));
    }
}
