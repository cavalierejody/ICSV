package it.jody.icsv;

import it.jody.icsv.exceptions.DateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMarshaller implements StringMarshaller<Date> {

    SimpleDateFormat formatter;

    private DateMarshaller(SimpleDateFormat dateFormat) {
        this.formatter = dateFormat;
    }

    public static DateMarshaller of(SimpleDateFormat dateFormat) {
        return new DateMarshaller(dateFormat);
    }

    @Override
    public String toString(Date value) {
        return formatter.format(value);
    }

    @Override
    public Date fromString(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            throw new DateFormatException(e);
        }
    }
}
