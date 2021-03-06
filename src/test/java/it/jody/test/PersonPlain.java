package it.jody.test;

import it.jody.icsv.CSVDateMashaller;
import it.jody.icsv.CSVField;
import it.jody.icsv.CSVFieldMarshaller;
import it.jody.icsv.StringMarshaller;

import java.util.Date;

/**
 * Created by Jody on 20/02/2018.
 */
public interface PersonPlain {

    @CSVField(idx = 0)
    @CSVFieldMarshaller(UpperCaseMarshaller.class)
    String getName();

    @CSVField(idx = 1)
    String getAge();

    @CSVField(idx = 2)
    Date getDateBirth();

    @CSVField(idx = 3)
    @CSVDateMashaller("yyyy/MM/dd")
    Date getDateMarriage();

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
