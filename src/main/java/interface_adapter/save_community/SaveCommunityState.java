package interface_adapter.save_community;

import java.util.ArrayList;
import java.util.List;

import entity.Community;

/**
 * Everything the watchlist screen shows. Owner: Shreyas.
 */
public class SaveCommunityState {

    private List<Community> searchResults = new ArrayList<>();
    private List<Community> watchlist = new ArrayList<>();
    private String message = "";
    private String error = "";

    public List<Community> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Community> searchResults) {
        this.searchResults = searchResults;
    }

    public List<Community> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Community> watchlist) {
        this.watchlist = watchlist;
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
