package it.jody.test;

import it.jody.icsv.CSVBeanHandler;
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
        PersonPlain personPlain = new CSVBeanHandler().toBean(PersonPlain.class, arr);

        Assert.assertEquals("Mario Rossi", personPlain.getName());
        Assert.assertEquals("25", personPlain.getAge());
    }

    @Test
    public void testCSVToBean() throws ManagedTypeException, MissingMarkerNameException {

        String[] arr = {"PER", "Mario Rossi", "25", "35000d"};
        CSVBeanHandler csvBeanHandler = new CSVBeanHandler().withManagedType(Person.class);
        Person person = (Person) csvBeanHandler.toBean(arr);

        Assert.assertEquals("PER", person.getMarkerName());
        Assert.assertEquals("Mario Rossi", person.getName());
        Assert.assertEquals(Integer.valueOf(25), person.getAge());
        Assert.assertEquals(Double.valueOf(35000), person.getSalary());
    }

    @Test
    public void testWriter() throws MissingMarkerNameException, ManagedTypeException {

        CSVBeanHandler csvBeanHandler = new CSVBeanHandler();
        Person person = csvBeanHandler.toBean(Person.class);

        person.setName("Mario Rossi");
        Assert.assertEquals("Mario Rossi", person.getName());

        person.setName("Giovanni Bianchi");
        Assert.assertEquals("Giovanni Bianchi", person.getName());

        Assert.assertEquals("PER;Giovanni Bianchi;null;null", person.toCsvString());
    }
}
