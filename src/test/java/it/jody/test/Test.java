package it.jody.test;

import it.jody.icsv.CSVBeanBuilder;
import org.junit.Assert;

/**
 * Created by Jody on 20/02/2018.
 */

public class Test {

    @org.junit.Test
    public void testPlainCSVtoBean() {

        String[] arr = {"Mario Rossi", "25"};
        PersonPlain personPlain = (PersonPlain) new CSVBeanBuilder().build(PersonPlain.class, arr);

        Assert.assertEquals("Mario Rossi", personPlain.getName());
        Assert.assertEquals("25", personPlain.getAge());
    }

    @org.junit.Test
    public void testCSVToBean() {

        String[] arr = {"PER", "Mario Rossi", "25"};
        CSVBeanBuilder csvBeanBuilder = new CSVBeanBuilder().withManagedType(Person.class);
        Person person = (Person) csvBeanBuilder.build( arr);

        Assert.assertEquals("PER", person.getMarkerName());
        Assert.assertEquals("Mario Rossi", person.getName());
        Assert.assertEquals("25", person.getAge());
    }
}
