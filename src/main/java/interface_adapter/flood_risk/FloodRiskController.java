package interface_adapter.flood_risk;

import java.time.LocalDate;

import use_case.flood_risk.FloodRiskInputBoundary;
import use_case.flood_risk.FloodRiskInputData;

/**
 * Turns the Assess button into a use case call. Owner: Member B.
 */
public class FloodRiskController {

    private final FloodRiskInputBoundary interactor;

    /**
     * Creates the controller.
     *
     * @param interactor the use case
     */
    public FloodRiskController(FloodRiskInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Asks for a flood rating.
     *
     * @param communityId the chosen community
     * @param asOf        the chosen date, or null for today
     */
    public void execute(String communityId, LocalDate asOf) {
        interactor.execute(new FloodRiskInputData(communityId, asOf));
    }
}
