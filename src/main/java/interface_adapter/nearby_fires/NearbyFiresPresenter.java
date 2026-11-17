package interface_adapter.nearby_fires;

import interface_adapter.ViewModel;
import use_case.nearby_fires.NearbyFiresOutputBoundary;
import use_case.nearby_fires.NearbyFiresOutputData;

/**
 * Formats nearby fires for display. Owner: Member C.
 */
public class NearbyFiresPresenter implements NearbyFiresOutputBoundary {

    private final ViewModel<NearbyFiresState> viewModel;

    /**
     * Creates the presenter.
     *
     * @param viewModel the wildfire view model
     */
    public NearbyFiresPresenter(ViewModel<NearbyFiresState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(NearbyFiresOutputData output) {
        final NearbyFiresState state = viewModel.getState();
        state.setError("");
        // TODO Member C: summary like "12 fires, nearest 18 km NW"; level; reasons;
        //  fire points; wind; "No fires found" when the list is empty
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setError(error);
        viewModel.firePropertyChange();
    }
}
