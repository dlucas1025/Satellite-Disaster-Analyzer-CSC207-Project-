package interface_adapter.save_community;

import interface_adapter.ViewModel;
import use_case.save_community.SaveCommunityOutputBoundary;
import use_case.save_community.SaveCommunityOutputData;

/**
 * Puts watchlist results into the view model. Owner: Member A.
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
        state.setError("");
        // TODO Member A: format "name, province" labels if the view needs them
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareWatchlistView(SaveCommunityOutputData output) {
        final SaveCommunityState state = viewModel.getState();
        state.setWatchlist(output.watchlist());
        state.setMessage(output.message() == null ? "" : output.message());
        state.setError("");
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setError(error);
        viewModel.firePropertyChange();
    }
}
