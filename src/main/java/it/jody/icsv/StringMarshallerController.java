package it.jody.icsv;

import java.util.HashMap;
import java.util.Map;

public class StringMarshallerController {

    final Map<Class, StringMarshaller> marshallerMap = new HashMap<Class, StringMarshaller>();

    public StringMarshallerController() {
        addMarshaller(String.class, new StringDefaultMarshaller());
        addMarshaller(Integer.class, new IntegerMarshaller());
    }

    public void addMarshaller(Class<?> aClass, StringMarshaller<?> aMarshaller) {
        marshallerMap.put(aClass, aMarshaller);
    }

    public StringMarshaller getMarshaller(Class aClass) {
        return marshallerMap.get(aClass);
    }

    private class StringDefaultMarshaller implements StringMarshaller<String> {

        @Override
        public String toString(String value) {
            return value;
        }

        @Override
        public String fromString(String value) {
            return value;
        }
    }

    private class IntegerMarshaller implements StringMarshaller<Integer> {

        @Override
        public String toString(Integer value) {
            return value.toString();
        }

        @Override
        public Integer fromString(String value) {
            return Integer.parseInt(value);
        }
    }
}
