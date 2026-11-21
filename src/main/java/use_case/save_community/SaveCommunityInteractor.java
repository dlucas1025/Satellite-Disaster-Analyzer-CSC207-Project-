package use_case.save_community;

import java.util.List;
import java.util.Optional;

import entity.Community;
import entity.Watchlist;
import use_case.shared.DataUnavailableException;

/**
 * Searches for places and keeps the watchlist. Owner: Shreyas.
 *
 * <p>Every change follows the same steps: load the saved watchlist, change it through
 * the entity (which enforces the rules), save it, then tell the presenter. If a store or
 * the search service fails, the user gets a message instead of a crash.</p>
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
        try {
            showWatchlist(watchlistStore.load(owner), null);
        }
        catch (DataUnavailableException ex) {
            presenter.prepareFailView("Could not load your watchlist: " + ex.getMessage());
        }
    }

    @Override
    public void search(SaveCommunityInputData input) {
        final String query = input.query() == null ? "" : input.query().trim();
        if (query.isEmpty()) {
            presenter.prepareFailView("Type a community name to search.");
            return;
        }
        try {
            final List<Community> results = placeSearch.searchPlaces(query);
            final String message;
            if (results.isEmpty()) {
                message = "No places found for \"" + query + "\".";
            }
            else {
                message = null;
            }
            presenter.prepareSearchResults(new SaveCommunityOutputData(List.copyOf(results), List.of(), message));
        }
        catch (DataUnavailableException ex) {
            // The saved watchlist is untouched, so the user can keep working with it.
            presenter.prepareFailView("Search is unavailable right now: " + ex.getMessage());
        }
    }

    @Override
    public void save(SaveCommunityInputData input) {
        final Community place = input.place();
        if (place == null) {
            presenter.prepareFailView("Pick a place from the search results first.");
            return;
        }
        try {
            final Watchlist watchlist = watchlistStore.load(input.owner());
            if (!watchlist.add(place)) {
                showWatchlist(watchlist, place.name() + " is already on your watchlist.");
                return;
            }
            watchlistStore.save(watchlist);
            showWatchlist(watchlist, "Saved " + place.name() + ".");
        }
        catch (DataUnavailableException ex) {
            presenter.prepareFailView("Could not save: " + ex.getMessage());
        }
    }

    @Override
    public void remove(SaveCommunityInputData input) {
        try {
            final Watchlist watchlist = watchlistStore.load(input.owner());
            final Optional<Community> existing = watchlist.find(input.communityId());
            if (existing.isEmpty()) {
                showWatchlist(watchlist, "That community is not on your watchlist.");
                return;
            }
            watchlist.remove(input.communityId());
            watchlistStore.save(watchlist);
            showWatchlist(watchlist, "Removed " + existing.get().name() + ".");
        }
        catch (DataUnavailableException ex) {
            presenter.prepareFailView("Could not remove: " + ex.getMessage());
        }
    }

    @Override
    public void rename(SaveCommunityInputData input) {
        final String newName = input.newName() == null ? "" : input.newName().trim();
        if (newName.isEmpty()) {
            presenter.prepareFailView("Type a new name.");
            return;
        }
        try {
            final Watchlist watchlist = watchlistStore.load(input.owner());
            if (!watchlist.rename(input.communityId(), newName)) {
                showWatchlist(watchlist, "That community is not on your watchlist.");
                return;
            }
            watchlistStore.save(watchlist);
            showWatchlist(watchlist, "Renamed to " + newName + ".");
        }
        catch (DataUnavailableException ex) {
            presenter.prepareFailView("Could not rename: " + ex.getMessage());
        }
    }

    private void showWatchlist(Watchlist watchlist, String message) {
        presenter.prepareWatchlistView(
                new SaveCommunityOutputData(List.of(), List.copyOf(watchlist.getCommunities()), message));
    }
}
