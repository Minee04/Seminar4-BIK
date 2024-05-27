package se.KTH.seminar3.integration;

/**
 * This class represents a custom exception that is thrown when a connection to
 * the database fails.
 */
public class ConnectionToDatabaseFailed extends Exception {

    private String errorMsg = "Server connection timed out, check your internet connection and try again.";

    /**
     * Constructs a new ConnectionToDatabaseFailed exception with the specified
     * error message. If the provided error message is empty, the default error
     * message is used.
     *
     * @param errorMsg The detail message (which is saved for later retrieval by
     * the Throwable.getMessage() method)
     */
    public ConnectionToDatabaseFailed(String errorMsg) {
        if (!"".equals(errorMsg)) {
            this.errorMsg = errorMsg;
        }
    }

    /**
     * Returns a string representation of this throwable. The string
     * representation contains the specified or default error message.
     *
     * @return A string representation of this throwable.
     */
    @Override
    public String toString() {
        return errorMsg;
    }
}
