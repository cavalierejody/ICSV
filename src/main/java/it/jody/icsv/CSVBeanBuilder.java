package it.jody.icsv;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Jody on 20/02/2018.
 */
public class CSVBeanBuilder {

    private HashMap<String, Class> managedTypes = new HashMap<String, Class>();

    /**
     * Return bean instance for an array.
     * It can be used both with managed types and unmaged types of aarray.
     * If array type is managed, then the managed types library will be updated.
     * @param array
     * @return
     */
    public Object build(Class type, Object[] array) {

        withManagedType(type);

        return Proxy.newProxyInstance(
                CSVBeanBuilder.class.getClassLoader(),
                new Class[] { type },
                new CSVInvocationHandler(array)
        );
    }

    /**
     * Return bean instance for an array with a managed type already loaded.
     * It cannot be used with unmanaged tyupes of array.
     * @param array
     * @return
     */
    public Object build(Object[] array) {

        String typeMarker = String.valueOf(array[0]);
        Class aClass = managedTypes.get(typeMarker);

        if (aClass == null) {
            throw new RuntimeException("unmanaged type of array");
        } else {
            return build(aClass, array);
        }
    }

    /**
     * Register a type Class for Bean conversion based on the marker attribute.
     * @param type
     * @return
     */
    public CSVBeanBuilder withManagedType(Class type) {

        CSVType csvType = (CSVType) type.getAnnotation(CSVType.class);
        if (csvType != null) {
            if (csvType.markerName().isEmpty()) {
                throw new RuntimeException("missing markerName annotation");
            } else {
                String key = csvType.markerName();
                managedTypes.put(key, type);
            }
        }

        return this;
    }

    /**
     * Register multiple types for Bean conversion based on the marker attribute.
     * It calls method {@link #withManagedType(java.lang.Class)}
     * @param types
     * @return
     */
    public CSVBeanBuilder withManagedTypes(Collection<Class> types) {

        for (Class type : types) {
            withManagedType(type);
        }
        return this;
    }
}
