package ru.center.inform.docs.server.exception;

public class DocumentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Document not found!";

    public DocumentNotFoundException() {
        super(MESSAGE);
    }

    public DocumentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
