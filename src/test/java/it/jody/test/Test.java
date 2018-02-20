package it.jody.test;

import it.jody.icsv.CSVBeanBuilder;

/**
 * Created by Jody on 20/02/2018.
 */
public class Test {

    static String[] tupla = {"Jody Cavalli", "28"};

    public static void main(String[] args) {

        Person person = new CSVBeanBuilder<Person>().build(Person.class, tupla);
        String name = person.getName();
        String age = person.getAge();

        System.out.println(name);
        System.out.println(age);
    }
}
