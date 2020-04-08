package it.jody.icsv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StringMarshallerController {

    private static final String NULL_DEFAULT = null;
    private final Map<Class, StringMarshaller> marshallerMap = new HashMap();

    public StringMarshallerController() {
        addMarshaller(String.class, new StringDefaultMarshaller());
        addMarshaller(Integer.class, new IntegerMarshaller());
        addMarshaller(Float.class, new FloatMarshaller());
        addMarshaller(Long.class, new LongMarshaller());
        addMarshaller(Double.class, new DoubleMarshaller());
        addMarshaller(Date.class, DateMarshaller.of(new SimpleDateFormat("yyyyMMdd")));
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
            return Objects.toString(value, NULL_DEFAULT);
        }

        @Override
        public String fromString(String value) {
            return value;
        }
    }

    private class IntegerMarshaller implements StringMarshaller<Integer> {

        @Override
        public String toString(Integer value) {
            return Objects.toString(value, NULL_DEFAULT);
        }

        @Override
        public Integer fromString(String value) {
            return Integer.parseInt(value);
        }
    }

    private class FloatMarshaller implements StringMarshaller<Float> {

        @Override
        public String toString(Float value) {
            return Objects.toString(value, NULL_DEFAULT);
        }

        @Override
        public Float fromString(String value) {
            return Float.parseFloat(value);
        }
    }

    private class LongMarshaller implements StringMarshaller<Long>{

        @Override
        public String toString(Long value) {
            return Objects.toString(value, NULL_DEFAULT);
        }

        @Override
        public Long fromString(String value) {
            return Long.parseLong(value);
        }
    }

    private class DoubleMarshaller implements StringMarshaller<Double>{

        @Override
        public String toString(Double value) {
            return Objects.toString(value, NULL_DEFAULT);
        }

        @Override
        public Double fromString(String value) {
            return Double.parseDouble(value);
        }
    }
}
