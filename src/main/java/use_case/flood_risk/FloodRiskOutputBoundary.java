package use_case.flood_risk;

/**
 * How the flood interactor reports results. Owner: Member B.
 */
public interface FloodRiskOutputBoundary {

    /**
     * Shows the rating.
     *
     * @param output the 7-day rating
     */
    void prepareSuccessView(FloodRiskOutputData output);

    /**
     * Shows "Cannot assess" and why, never a misleading Low.
     *
     * @param error what data was missing
     */
    void prepareFailView(String error);
}
