package use_case.compare_imagery;

/**
 * Entry point for story 4. Owner: Youssef.
 */
public interface CompareImageryInputBoundary {

    /**
     * Compares the two pictures.
     *
     * @param input the community, hazard and dates
     */
    void execute(CompareImageryInputData input);
}
