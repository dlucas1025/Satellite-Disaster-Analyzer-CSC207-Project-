package use_case.compare_imagery;

/**
 * Entry point for story 4. Owner: Member D.
 */
public interface CompareImageryInputBoundary {

    /**
     * Compares the two pictures.
     *
     * @param input the community, hazard and dates
     */
    void execute(CompareImageryInputData input);
}
