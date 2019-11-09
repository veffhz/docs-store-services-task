package ru.center.inform.docs.server.exception;

public class DecryptDataException extends RuntimeException {

    private static final String MESSAGE = "Decrypt data error!";

    public DecryptDataException() {
        super(MESSAGE);
    }

    public DecryptDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptDataException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
