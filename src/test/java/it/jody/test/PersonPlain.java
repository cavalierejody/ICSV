package it.jody.test;

import it.jody.icsv.CSVField;
import it.jody.icsv.StringMarshaller;

/**
 * Created by Jody on 20/02/2018.
 */
public interface PersonPlain {

    @CSVField(idx = 0, marshaller = UpperCaseMarshaller.class)
    String getName();

    @CSVField(idx = 1)
    String getAge();

    class UpperCaseMarshaller implements StringMarshaller<String> {

        @Override
        public String toString(String value) {
            return value == null ? null : value.toUpperCase();
        }

        @Override
        public String fromString(String value) {
            return value == null ? null : value.toUpperCase();
        }
    }

}
