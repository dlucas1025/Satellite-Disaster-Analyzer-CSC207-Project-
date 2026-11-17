package interface_adapter.compare_imagery;

import interface_adapter.ViewModel;
import use_case.compare_imagery.CompareImageryOutputBoundary;
import use_case.compare_imagery.CompareImageryOutputData;

/**
 * Formats the comparison for display. Owner: Member D.
 */
public class CompareImageryPresenter implements CompareImageryOutputBoundary {

    private final ViewModel<CompareImageryState> viewModel;

    /**
     * Creates the presenter.
     *
     * @param viewModel the comparison view model
     */
    public CompareImageryPresenter(ViewModel<CompareImageryState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CompareImageryOutputData output) {
        final CompareImageryState state = viewModel.getState();
        state.setError("");
        // TODO Member D: copy pixels and size; captions like "2 May 2022: 14% water (mostly cloud!)";
        //  summary like "Water cover rose from 3% to 14%"
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setError(error);
        viewModel.firePropertyChange();
    }
}
