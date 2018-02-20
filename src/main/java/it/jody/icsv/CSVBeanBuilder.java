package it.jody.icsv;

import java.lang.reflect.Proxy;

/**
 * Created by Jody on 20/02/2018.
 */
public class CSVBeanBuilder<T> {

    public T build(Class type, Object[] array) {

        return (T) Proxy.newProxyInstance(
                CSVBeanBuilder.class.getClassLoader(),
                new Class[] { type },
                new CSVInvocationHandler(array)
        );
    }
}
