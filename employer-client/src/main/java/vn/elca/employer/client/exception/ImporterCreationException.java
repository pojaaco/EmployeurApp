package vn.elca.employer.client.exception;

public class ImporterCreationException extends Exception {
    public ImporterCreationException(String msg, Throwable orgEx) {
        super(msg, orgEx);
    }
}
