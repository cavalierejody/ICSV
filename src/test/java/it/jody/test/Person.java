package it.jody.test;

import it.jody.icsv.CSVDeclaredType;
import it.jody.icsv.CSVType;
import it.jody.icsv.CSVField;

/**
 * Created by Jody on 22/02/2018.
 */
@CSVType(markerName = "PER", size = 3)
public interface Person extends CSVDeclaredType {

    @CSVField(idx = 1)
    String getName();

    @CSVField(idx = 2)
    Integer getAge();
}
