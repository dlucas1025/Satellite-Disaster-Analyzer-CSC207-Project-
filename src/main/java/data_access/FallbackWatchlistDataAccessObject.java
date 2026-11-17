package data_access;

import entity.Watchlist;
import use_case.save_community.WatchlistDataAccessInterface;

/**
 * Decorator: uses the primary store (cloud) and falls back to the backup store (file)
 * when the primary is unreachable. Owner: Member A.
 */
public class FallbackWatchlistDataAccessObject implements WatchlistDataAccessInterface {

    private final WatchlistDataAccessInterface primary;
    private final WatchlistDataAccessInterface backup;

    /**
     * Creates the decorator.
     *
     * @param primary the preferred store, e.g. cloud
     * @param backup  the store used when the primary fails, e.g. file
     */
    public FallbackWatchlistDataAccessObject(WatchlistDataAccessInterface primary,
                                             WatchlistDataAccessInterface backup) {
        this.primary = primary;
        this.backup = backup;
    }

    @Override
    public Watchlist load(String owner) {
        // TODO Member A: try primary; on DataUnavailableException use backup
        return backup.load(owner);
    }

    @Override
    public void save(Watchlist watchlist) {
        // TODO Member A: always save to backup; also try primary and remember to sync later if it fails
        backup.save(watchlist);
    }
}
