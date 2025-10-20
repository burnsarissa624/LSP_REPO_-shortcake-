package org.howard.edu.lsp.assignment3.pipeline;

/**
 * Custom exception to indicate a validation failure in the ETL pipeline.
 */
public class ValidationException extends Exception {
    /**
     * Create a new ValidationException.
     *
     * @param message explanation of the validation failure
     */
    public ValidationException(String message) {
        super(message);
    }
}
