package it.jody.test;

import it.jody.icsv.CSVDeclaredType;
import it.jody.icsv.CSVType;
import it.jody.icsv.CSVField;
import it.jody.icsv.CSVConvertible;

/**
 * Created by Jody on 22/02/2018.
 */
@CSVType(markerName = "PER", size = 4)
public interface Person extends CSVDeclaredType, CSVConvertible {

    @CSVField(idx = 1)
    String getName();

    @CSVField(idx = 1)
    void setName(String value);

    @CSVField(idx = 2)
    Integer getAge();

    @CSVField(idx = 2)
    void setAge(Integer value);

    @CSVField(idx = 3)
    Double getSalary();

    @CSVField(idx = 3)
    void setSalary(Double value);
}
