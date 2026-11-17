package use_case.save_community;

/**
 * The actions the watchlist screen can ask for. Owner: Member A.
 */
public interface SaveCommunityInputBoundary {

    /**
     * Shows the saved watchlist (e.g. at start-up).
     *
     * @param owner the user
     */
    void load(String owner);

    /**
     * Searches for places by name.
     *
     * @param input search input
     */
    void search(SaveCommunityInputData input);

    /**
     * Saves a chosen place to the watchlist.
     *
     * @param input save input
     */
    void save(SaveCommunityInputData input);

    /**
     * Removes a saved community.
     *
     * @param input remove input
     */
    void remove(SaveCommunityInputData input);

    /**
     * Renames a saved community.
     *
     * @param input rename input
     */
    void rename(SaveCommunityInputData input);
}
