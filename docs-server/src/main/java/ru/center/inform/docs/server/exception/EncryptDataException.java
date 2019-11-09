package ru.center.inform.docs.server.exception;

public class EncryptDataException extends RuntimeException {

    private static final String MESSAGE = "Encrypt data error!";

    public EncryptDataException(String message) {
        super(message);
    }

    public EncryptDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptDataException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
