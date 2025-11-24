package org.example.exception;

/**
 * Excepción personalizada para ADN inválido.
 */
public class InvalidDnaException extends RuntimeException {

    public InvalidDnaException(String message) {
        super(message);
    }

    public InvalidDnaException(String message, Throwable cause) {
        super(message, cause);
    }
}
