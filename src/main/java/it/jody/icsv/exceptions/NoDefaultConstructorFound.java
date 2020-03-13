package it.jody.icsv.exceptions;

public class NoDefaultConstructorFound extends Exception {

    public NoDefaultConstructorFound() {
    }

    public NoDefaultConstructorFound(String message) {
        super(message);
    }

    public NoDefaultConstructorFound(String message, Throwable cause) {
        super(message, cause);
    }
}
