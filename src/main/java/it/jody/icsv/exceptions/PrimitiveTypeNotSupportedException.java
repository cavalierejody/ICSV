package it.jody.icsv.exceptions;

import it.jody.icsv.CSVField;

import java.lang.reflect.Method;

public class PrimitiveTypeNotSupportedException extends Exception {

    public PrimitiveTypeNotSupportedException(Method method) {
        super("[ " + method.getReturnType() + " ] at " + method.getAnnotation(CSVField.class) + " " + method.getDeclaringClass() + "." + method.getName());
    }
}
