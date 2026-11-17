package use_case.save_community;

/**
 * How the watchlist interactor reports results. Owner: Member A.
 */
public interface SaveCommunityOutputBoundary {

    /**
     * Shows search results.
     *
     * @param output the results and current watchlist
     */
    void prepareSearchResults(SaveCommunityOutputData output);

    /**
     * Shows the updated watchlist after load, save, remove or rename.
     *
     * @param output the watchlist and an optional message
     */
    void prepareWatchlistView(SaveCommunityOutputData output);

    /**
     * Shows an error.
     *
     * @param error what went wrong
     */
    void prepareFailView(String error);
}
