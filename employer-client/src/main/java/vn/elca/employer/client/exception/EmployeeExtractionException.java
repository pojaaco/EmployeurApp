package vn.elca.employer.client.exception;

public class EmployeeExtractionException extends Exception {
    public EmployeeExtractionException(String msg, Throwable orgEx) {
        super(msg, orgEx);
    }
}
