package it.jody.test;

import it.jody.icsv.CSVBeanBuilder;
import it.jody.icsv.exceptions.ManagedTypeException;
import it.jody.icsv.exceptions.MissingMarkerNameException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jody on 20/02/2018.
 */

public class ICSVTest {

    @Test
    public void testPlainCSVtoBean() throws MissingMarkerNameException {

        String[] arr = {"Mario Rossi", "25"};
        PersonPlain personPlain = new CSVBeanBuilder().build(PersonPlain.class, arr);

        Assert.assertEquals("Mario Rossi", personPlain.getName());
        Assert.assertEquals("25", personPlain.getAge());
    }

    @Test
    public void testCSVToBean() throws ManagedTypeException, MissingMarkerNameException {

        String[] arr = {"PER", "Mario Rossi", "25"};
        CSVBeanBuilder csvBeanBuilder = new CSVBeanBuilder().withManagedType(Person.class);
        Person person = (Person) csvBeanBuilder.build(arr);

        Assert.assertEquals("PER", person.getMarkerName());
        Assert.assertEquals("Mario Rossi", person.getName());
        Assert.assertEquals(Integer.valueOf(25), person.getAge());
    }
}
