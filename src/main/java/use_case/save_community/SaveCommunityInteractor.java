package use_case.save_community;

/**
 * Searches for places and keeps the watchlist. Owner: Shreyas.
 */
public class SaveCommunityInteractor implements SaveCommunityInputBoundary {

    private final PlaceSearchDataAccessInterface placeSearch;
    private final WatchlistDataAccessInterface watchlistStore;
    private final SaveCommunityOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param placeSearch    finds places by name
     * @param watchlistStore loads and saves watchlists
     * @param presenter      receives the results
     */
    public SaveCommunityInteractor(PlaceSearchDataAccessInterface placeSearch,
                                   WatchlistDataAccessInterface watchlistStore,
                                   SaveCommunityOutputBoundary presenter) {
        this.placeSearch = placeSearch;
        this.watchlistStore = watchlistStore;
        this.presenter = presenter;
    }

    @Override
    public void load(String owner) {
        // TODO Shreyas: load the watchlist and call presenter.prepareWatchlistView
        presenter.prepareFailView("Watchlist is not implemented yet");
    }

    @Override
    public void search(SaveCommunityInputData input) {
        // TODO Shreyas: reject blank queries; call placeSearch; catch DataUnavailableException
        presenter.prepareFailView("Search is not implemented yet");
    }

    @Override
    public void save(SaveCommunityInputData input) {
        // TODO Shreyas: load, add (tell the user if it was already saved), save
        presenter.prepareFailView("Save is not implemented yet");
    }

    @Override
    public void remove(SaveCommunityInputData input) {
        // TODO Shreyas: load, remove, save
        presenter.prepareFailView("Remove is not implemented yet");
    }

    @Override
    public void rename(SaveCommunityInputData input) {
        // TODO Shreyas: load, rename, save
        presenter.prepareFailView("Rename is not implemented yet");
    }
}
