package use_case.nearby_fires;

/**
 * Entry point for story 3. Owner: Member C.
 */
public interface NearbyFiresInputBoundary {

    /**
     * Finds and rates nearby fires.
     *
     * @param input the community, radius and period
     */
    void execute(NearbyFiresInputData input);
}
