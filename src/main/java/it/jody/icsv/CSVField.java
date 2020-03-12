package it.jody.icsv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jody on 20/02/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CSVField {
    int idx();
    Class<? extends StringMarshaller> marshaller() default StringMarshaller.class;
}
