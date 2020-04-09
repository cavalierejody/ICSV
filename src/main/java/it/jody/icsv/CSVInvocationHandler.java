package it.jody.icsv;

import it.jody.icsv.exceptions.InvalidTypeException;
import it.jody.icsv.exceptions.NoDefaultConstructorFound;
import it.jody.icsv.exceptions.PrimitiveTypeNotSupportedException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jody on 20/02/2018.
 */
public class CSVInvocationHandler implements InvocationHandler, CSVConvertible {

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
            return this.toCsvString();
        }

        if ("toCsvArray".equals(method.getName())) {
            return this.toCsvArray();
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
            StringMarshaller marshaller = getStringMarshaller(method, returnType);
            return marshaller.fromString((String) array[idx]);
        }

        if (isSetterMethod(method)) {

            Class<?> parameterType = method.getParameterTypes()[0];

            // return type primitive -> not Supported
            if (parameterType.isPrimitive()) {
                throw new PrimitiveTypeNotSupportedException(method);
            }

            StringMarshaller marshaller = getStringMarshaller(method, parameterType);
            String value = marshaller.toString(args[0]);
            array[idx] = value;
            return null;
        }

        throw new IllegalStateException();
    }

    private StringMarshaller getStringMarshaller(Method method, Class<?> type) throws NoDefaultConstructorFound, InvalidTypeException {
        CSVFieldMarshaller fieldMarshallerAnnotation = method.getAnnotation(CSVFieldMarshaller.class);
        CSVDateMashaller dateMarshallerAnnotation = method.getAnnotation(CSVDateMashaller.class);
        Class<? extends StringMarshaller> marshallerClass = fieldMarshallerAnnotation == null ? null : fieldMarshallerAnnotation.value();

        StringMarshaller marshaller;
        if (dateMarshallerAnnotation != null) {
            if (!type.isAssignableFrom(Date.class)) {
                throw new InvalidTypeException("cannot use @" + CSVDateMashaller.class.getSimpleName() + " with return type of " + type.getName());
            }
            marshaller = DateMarshaller.of(new SimpleDateFormat(dateMarshallerAnnotation.value()));
        } else if (marshallerClass == null || marshallerClass.getSuperclass() == null) {
            marshaller = marshallerController.getMarshaller(type);
        } else {
            try {
                marshaller = marshallerClass.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new NoDefaultConstructorFound("No default contructor found for marshaller " + marshallerClass.getName(), e);
            }
        }
        return marshaller;
    }

    private boolean isSetterMethod(Method method) {
        return method.getReturnType().equals(Void.TYPE) && method.getParameterCount() == 1;
    }

    private boolean isGetterMethod(Method method) {
        return !method.getReturnType().equals(Void.TYPE) && method.getParameterCount() == 0;
    }

    @Override
    public String toCsvString() {
        return String.join(";", Stream.of(array).map(String::valueOf).collect(Collectors.toList()));
    }

    @Override
    public String[] toCsvArray() {
        return (String[]) Stream.of(array).map(String::valueOf).toArray();
    }
}
