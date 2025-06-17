package com.example.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GenericObjectFactory<T> implements PooledObjectFactory<T> {
    private final Class<T> clazz;

    public GenericObjectFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public PooledObject<T> makeObject() throws Exception {
        // Call private constructor reflectively
        var ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        T instance = ctor.newInstance();
        return new DefaultPooledObject<>(instance);
    }

    @Override
    public void destroyObject(PooledObject<T> p) throws Exception {
        // Nothing special to destroy
    }

    @Override
    public boolean validateObject(PooledObject<T> p) {
        return p.getObject() != null;
    }

    @Override
    public void activateObject(PooledObject<T> p) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<T> p) throws Exception {
    }
}
