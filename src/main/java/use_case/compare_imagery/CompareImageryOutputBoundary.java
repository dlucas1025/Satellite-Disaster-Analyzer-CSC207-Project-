package use_case.compare_imagery;

/**
 * How the comparison interactor reports results. Owner: Member D.
 */
public interface CompareImageryOutputBoundary {

    /**
     * Shows both pictures and the change.
     *
     * @param output the result
     */
    void prepareSuccessView(CompareImageryOutputData output);

    /**
     * Shows an error, e.g. no picture for a date.
     *
     * @param error what went wrong
     */
    void prepareFailView(String error);
}
