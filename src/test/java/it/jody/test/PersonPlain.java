package it.jody.test;

import it.jody.icsv.CSVField;

/**
 * Created by Jody on 20/02/2018.
 */
public interface PersonPlain {

    @CSVField(idx = 0)
    String getName();

    @CSVField(idx = 1)
    String getAge();
}
