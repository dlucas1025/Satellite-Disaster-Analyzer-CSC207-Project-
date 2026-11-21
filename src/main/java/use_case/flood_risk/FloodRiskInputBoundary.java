package use_case.flood_risk;

/**
 * Entry point for story 2. Owner: Lucas.
 */
public interface FloodRiskInputBoundary {

    /**
     * Rates flood risk for the next 7 days.
     *
     * @param input the community and date
     */
    void execute(FloodRiskInputData input);
}
