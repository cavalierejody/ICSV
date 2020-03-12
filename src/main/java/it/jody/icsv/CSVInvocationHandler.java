package it.jody.icsv;

import it.jody.icsv.exceptions.PrimitiveTypeNotSupportedException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public CSVInvocationHandler(Object... values) {
        this.array = values;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        CSVField annotation = method.getAnnotation(CSVField.class);
        if ("getMarkerName".equals(method.getName())) {
            return array[0];
        }

        if ("toCsvString".equals(method.getName())) {
            return String.join(";", Stream.of(array).map(String::valueOf).collect(Collectors.toList()));
        }

        if ("toCsvArray".equals(method.getName())) {
            return Stream.of(array).map(String::valueOf).toArray();
        }

        // annotation CSVFIeld not found -> call the method itself
        if (annotation == null) {
            return method.invoke(args);
        }

        int idx = annotation.idx();

        // getter method
        if (isGetterMethod(method)) {
            Class<?> returnType = method.getReturnType();

            // return type primitive -> not Supported
            if (returnType.isPrimitive()) {
                throw new PrimitiveTypeNotSupportedException(method);
            }

            StringMarshaller marshaller = getStringMarshaller(annotation, returnType);
            return marshaller.fromString((String) array[idx]);
        }

        if (isSetterMethod(method)) {

            Class<?> parameterType = method.getParameterTypes()[0];

            // return type primitive -> not Supported
            if (parameterType.isPrimitive()) {
                throw new PrimitiveTypeNotSupportedException(method);
            }

            StringMarshaller marshaller = getStringMarshaller(annotation, parameterType);
            String value = marshaller.toString(args[0]);
            array[idx] = value;
            return null;
        }

        throw new IllegalStateException();
    }

    private StringMarshaller getStringMarshaller(CSVField annotation, Class<?> type) throws InstantiationException, IllegalAccessException {
        Class<? extends StringMarshaller> marshallerClass = annotation.marshaller();
        StringMarshaller marshaller;
        if (marshallerClass.getSuperclass() == null) {
            marshaller = marshallerController.getMarshaller(type);
        } else {
            try {
                marshaller = marshallerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("No default contructor found for marshaller " + marshallerClass.getName(), e);
            }
        }
        return marshaller;
    }

    private boolean isSetterMethod(Method method) {
        return method.getReturnType().equals(Void.TYPE) && method.getParameterCount() == 1;
    }

    private boolean isGetterMethod(Method method) {
        return ! method.getReturnType().equals(Void.TYPE) && method.getParameterCount() == 0;
    }
}
