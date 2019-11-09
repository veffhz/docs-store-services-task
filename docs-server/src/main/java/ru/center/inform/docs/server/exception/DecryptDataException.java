package ru.center.inform.docs.server.exception;

public class DecryptDataException extends RuntimeException {

    private static final String MESSAGE = "Decrypt data error!";

    public DecryptDataException(String message) {
        super(message);
    }

    public DecryptDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptDataException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
