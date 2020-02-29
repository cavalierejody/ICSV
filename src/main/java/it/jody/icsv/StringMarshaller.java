package it.jody.icsv;

public interface StringMarshaller<T> {

    String toString(T value);

    T fromString(String value);
}
