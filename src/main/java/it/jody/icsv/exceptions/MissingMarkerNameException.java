package it.jody.icsv.exceptions;

public class MissingMarkerNameException extends Exception {

    public MissingMarkerNameException() {
        super("Missing markerName value in ManagedType annotation");
    }
}
