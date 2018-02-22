package it.jody.icsv;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Jody on 20/02/2018.
 */
public class CSVBeanBuilder<T> {

    private HashMap<String, Class> managedTypes = new HashMap<String, Class>();

    public T build(Class type) {

        CSVType annotationMarker = (CSVType) type.getAnnotation(CSVType.class);
        Object[] array = new Object[annotationMarker.size()];
        if (! annotationMarker.markerName().isEmpty()) {
            managedTypes.put(annotationMarker.markerName(), type);
            array[0] = annotationMarker.markerName();
        }
        return (T) Proxy.newProxyInstance(
                CSVBeanBuilder.class.getClassLoader(),
                new Class[] { type },
                new CSVInvocationHandler(array)
        );
    }

    // try wih
    public Object build(Class type, Object[] array) {

        CSVType annotationMarker = (CSVType) type.getAnnotation(CSVType.class);
        if (annotationMarker != null) {
            String markerName = annotationMarker.markerName();
            if (markerName != null && !markerName.isEmpty()) {
                if (! markerName.equals(array[0])) {
                    throw new RuntimeException("invalid markerName for array");
                }
            }
        }
        //TODO refactor in factory for builder
        return (T) Proxy.newProxyInstance(
                CSVBeanBuilder.class.getClassLoader(),
                new Class[] { type },
                new CSVInvocationHandler(array)
        );
    }

    public Object build(Object[] array) {

        String typeMarker = String.valueOf(array[0]);
        Class aClass = managedTypes.get(typeMarker);
        if (aClass == null) {
            throw new RuntimeException("unmanaged type of array");
        } else {
            return build(aClass, array);
        }
    }

    public CSVBeanBuilder withManagedType(Class type) {

        CSVType CSVType = (CSVType) type.getAnnotation(CSVType.class);
        if (CSVType == null)
            throw new RuntimeException("missing CSVType annotation for type " + type.getCanonicalName());
        if (CSVType.markerName().isEmpty()) {
            throw new RuntimeException("missing markerName annotation");
        }
        String key = CSVType.markerName();
        managedTypes.put(key, type);
        return this;
    }

    public CSVBeanBuilder withManagedTypes(Collection<Class> types) {

        for (Class type : types) {
            withManagedType(type);
        }
        return this;
    }
}
