package interface_adapter.save_community;

import java.util.List;
import java.util.Locale;

import entity.Community;
import interface_adapter.ViewModel;
import use_case.save_community.SaveCommunityOutputBoundary;
import use_case.save_community.SaveCommunityOutputData;

/**
 * Puts watchlist results into the view model, formatted for display. Owner: Shreyas.
 */
public class SaveCommunityPresenter implements SaveCommunityOutputBoundary {

    private final ViewModel<SaveCommunityState> viewModel;

    /**
     * Creates the presenter.
     *
     * @param viewModel the watchlist view model
     */
    public SaveCommunityPresenter(ViewModel<SaveCommunityState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSearchResults(SaveCommunityOutputData output) {
        final SaveCommunityState state = viewModel.getState();
        state.setSearchResults(output.searchResults());
        state.setSearchLabels(labels(output.searchResults()));
        state.setMessage(messageOrBlank(output));
        state.setError("");
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareWatchlistView(SaveCommunityOutputData output) {
        final SaveCommunityState state = viewModel.getState();
        state.setWatchlist(output.watchlist());
        state.setWatchlistLabels(labels(output.watchlist()));
        state.setMessage(messageOrBlank(output));
        state.setError("");
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setError(error);
        viewModel.firePropertyChange();
    }

    /**
     * Display text for one community, with its location so places that share a name
     * can be told apart, e.g. "Peguis, MB  (50.08°N, 96.95°W)".
     *
     * @param community the community
     * @return the label
     */
    public static String label(Community community) {
        final StringBuilder text = new StringBuilder(community.name());
        if (!community.province().isBlank()) {
            text.append(", ").append(community.province());
        }
        text.append(String.format(Locale.ROOT, "  (%.2f°%s, %.2f°%s)",
                Math.abs(community.latitude()), community.latitude() >= 0 ? "N" : "S",
                Math.abs(community.longitude()), community.longitude() >= 0 ? "E" : "W"));
        return text.toString();
    }

    private static List<String> labels(List<Community> communities) {
        return communities.stream().map(SaveCommunityPresenter::label).toList();
    }

    private static String messageOrBlank(SaveCommunityOutputData output) {
        if (output.message() == null) {
            return "";
        }
        return output.message();
    }
}
