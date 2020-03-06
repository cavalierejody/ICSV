package it.jody.icsv;

import it.jody.icsv.exceptions.ManagedTypeException;
import it.jody.icsv.exceptions.MissingMarkerNameException;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by Jody on 20/02/2018.
 */
public class CSVBeanHandler {

    private final HashMap<String, Class> managedTypes = new HashMap<String, Class>();

    /**
     * Register a type Class for Bean conversion based on the marker attribute.
     *
     * @param type
     * @return
     */
    public CSVBeanHandler withManagedType(Class type) throws MissingMarkerNameException {

        CSVType csvType = (CSVType) type.getAnnotation(CSVType.class);
        if (csvType != null) {
            if (csvType.markerName().isEmpty()) {
                throw new MissingMarkerNameException();
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
     *
     * @param types
     * @return
     */
    public CSVBeanHandler withManagedTypes(Class... types) throws MissingMarkerNameException {

        for (Class type : types) {
            withManagedType(type);
        }
        return this;
    }

    /**
     * Return bean instance for an array.
     * It can be used both with managed types and unmaged types of aarray.
     * If array type is managed, then the managed types library will be updated.
     *
     * @param array
     * @return
     */
    public <T> T toBean(Class<T> type, Object[] array) throws MissingMarkerNameException {

        withManagedType(type);

        return type.cast(
                Proxy.newProxyInstance(
                        CSVBeanHandler.class.getClassLoader(),
                        new Class[]{type},
                        new CSVInvocationHandler(array)
                )
        );
    }

    /**
     * Return bean instance for an array with a managed type already loaded.
     * It cannot be used with unmanaged tyupes of array.
     *
     * @param array
     * @return
     */
    public Object toBean(Object[] array) throws ManagedTypeException, MissingMarkerNameException {

        String typeMarker = String.valueOf(array[0]);
        Class aClass = managedTypes.get(typeMarker);

        if (aClass == null) {
            throw new ManagedTypeException();
        } else {
            return toBean(aClass, array);
        }
    }
}
