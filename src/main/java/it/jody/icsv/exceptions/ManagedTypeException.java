package it.jody.icsv.exceptions;

public class ManagedTypeException extends Exception {

    public ManagedTypeException() {
        super("unmanaged type of array");
    }
}
