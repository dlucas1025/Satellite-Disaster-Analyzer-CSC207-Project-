package use_case.save_community;

import entity.Watchlist;
import use_case.shared.DataUnavailableException;

/**
 * Loads and saves a user's watchlist. Owner: Member A.
 *
 * <p>Implemented by the file store, the cloud store and the fallback decorator,
 * so the interactor never knows where the watchlist lives (Dependency Inversion).</p>
 */
public interface WatchlistDataAccessInterface {

    /**
     * Loads a watchlist.
     *
     * @param owner the user
     * @return the saved watchlist, or an empty one if none exists yet
     * @throws DataUnavailableException if the store cannot be read
     */
    Watchlist load(String owner);

    /**
     * Saves a watchlist, replacing the previous copy.
     *
     * @param watchlist the watchlist to save
     * @throws DataUnavailableException if the store cannot be written
     */
    void save(Watchlist watchlist);
}
