package data_access;

import entity.Watchlist;
import use_case.save_community.WatchlistDataAccessInterface;
import use_case.shared.DataUnavailableException;

/**
 * Keeps watchlists on the course's user-data server so they follow the user
 * to another computer. Owner: Member A.
 */
public class CloudWatchlistDataAccessObject implements WatchlistDataAccessInterface {

    private final String baseUrl;

    /**
     * Creates the store.
     *
     * @param baseUrl the course API URL (from config.properties: userdata.base.url)
     */
    public CloudWatchlistDataAccessObject(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Watchlist load(String owner) {
        // TODO Member A: GET the user's info JSON, read the watchlist from it
        throw new DataUnavailableException("Cloud watchlist is not implemented yet (" + baseUrl + ")");
    }

    @Override
    public void save(Watchlist watchlist) {
        // TODO Member A: PUT the watchlist JSON into the user's info
        throw new DataUnavailableException("Cloud watchlist is not implemented yet (" + baseUrl + ")");
    }
}
