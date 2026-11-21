package interface_adapter.flood_risk;

import interface_adapter.ViewModel;
import use_case.flood_risk.FloodRiskOutputBoundary;
import use_case.flood_risk.FloodRiskOutputData;

/**
 * Formats the flood rating for display. Owner: Lucas.
 */
public class FloodRiskPresenter implements FloodRiskOutputBoundary {

    private final ViewModel<FloodRiskState> viewModel;

    /**
     * Creates the presenter.
     *
     * @param viewModel the flood risk view model
     */
    public FloodRiskPresenter(ViewModel<FloodRiskState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FloodRiskOutputData output) {
        final FloodRiskState state = viewModel.getState();
        state.setHeading(output.communityName());
        state.setError("");
        // TODO Lucas: highest level -> overallLevel; one "Mon 2 May: HIGH" label per day;
        //  reasons from the highest day's factors; chart data; note if !output.usedGauge()
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.getState().setError(error);
        viewModel.firePropertyChange();
    }
}
