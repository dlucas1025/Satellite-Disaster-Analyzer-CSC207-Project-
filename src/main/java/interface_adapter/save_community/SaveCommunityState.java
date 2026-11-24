package interface_adapter.save_community;

import java.util.ArrayList;
import java.util.List;

import entity.Community;

/**
 * Everything the watchlist screen shows. Owner: Shreyas.
 *
 * <p>The label lists are display text written by the presenter, one per community and in
 * the same order, so the view never formats data itself. The community lists are kept so
 * the view can pass the chosen item back to the controller.</p>
 */
public class SaveCommunityState {

    private List<Community> searchResults = new ArrayList<>();
    private List<String> searchLabels = new ArrayList<>();
    private List<Community> watchlist = new ArrayList<>();
    private List<String> watchlistLabels = new ArrayList<>();
    private String message = "";
    private String error = "";

    public List<Community> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Community> searchResults) {
        this.searchResults = searchResults;
    }

    public List<String> getSearchLabels() {
        return searchLabels;
    }

    public void setSearchLabels(List<String> searchLabels) {
        this.searchLabels = searchLabels;
    }

    public List<Community> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Community> watchlist) {
        this.watchlist = watchlist;
    }

    public List<String> getWatchlistLabels() {
        return watchlistLabels;
    }

    public void setWatchlistLabels(List<String> watchlistLabels) {
        this.watchlistLabels = watchlistLabels;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
