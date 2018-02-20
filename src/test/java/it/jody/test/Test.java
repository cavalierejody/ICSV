package it.jody.test;

import it.jody.icsv.CSVBeanBuilder;
import org.junit.Assert;

/**
 * Created by Jody on 20/02/2018.
 */

public class Test {

    @org.junit.Test
    public void testCSVtoBean() {

        String[] arr = {"Mario Rossi", "25"};
        Person person = new CSVBeanBuilder<Person>().build(Person.class, arr);

        Assert.assertEquals("Mario Rossi", person.getName());
        Assert.assertEquals("25", person.getAge());
    }
}
