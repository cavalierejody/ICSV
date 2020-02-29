package it.jody.icsv;

import it.jody.icsv.exceptions.PrimitiveTypeNotSupportedException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Jody on 20/02/2018.
 */
public class CSVInvocationHandler implements InvocationHandler {

    private final StringMarshallerController marshallerController = new StringMarshallerController();

    private final Object[] array;

    public CSVInvocationHandler(List list) {
        Object[] arr = new Object[list.size()];
        arr = list.toArray(arr);
        array = arr;
    }

    public CSVInvocationHandler(Object[] array) {
        this.array = array;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        CSVField annotation = method.getAnnotation(CSVField.class);
        if ("getMarkerName".equals(method.getName())) {
            return array[0];
        }

        if (annotation == null) {
            return method.invoke(args);
        }

        if (method.getReturnType().isPrimitive()) {
            throw new PrimitiveTypeNotSupportedException(method);
        }

        int idx = annotation.idx();
        return marshallerController.getMarshaller(method.getReturnType()).fromString((String) array[idx]);
    }
}
