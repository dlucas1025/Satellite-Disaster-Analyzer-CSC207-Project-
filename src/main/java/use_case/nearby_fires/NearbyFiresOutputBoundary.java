package use_case.nearby_fires;

/**
 * How the wildfire interactor reports results. Owner: Member C.
 */
public interface NearbyFiresOutputBoundary {

    /**
     * Shows the fires and rating.
     *
     * @param output the result
     */
    void prepareSuccessView(NearbyFiresOutputData output);

    /**
     * Shows an error.
     *
     * @param error what went wrong
     */
    void prepareFailView(String error);
}
