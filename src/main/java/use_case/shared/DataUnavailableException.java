package use_case.shared;

/**
 * Thrown by a data access object when its data cannot be fetched or read
 * (no internet, service down, bad response, missing file).
 *
 * <p>Shared contract. Interactors catch it and call their presenter's fail view,
 * so the user sees a message instead of a crash.</p>
 */
public class DataUnavailableException extends RuntimeException {

    /**
     * Creates the exception.
     *
     * @param message what could not be fetched, in words a user understands
     */
    public DataUnavailableException(String message) {
        super(message);
    }

    /**
     * Creates the exception with its cause.
     *
     * @param message what could not be fetched
     * @param cause   the underlying error
     */
    public DataUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
