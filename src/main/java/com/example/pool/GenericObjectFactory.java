package com.example.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.lang.reflect.Method;

public class GenericObjectFactory<T> implements PooledObjectFactory<T> {

    private final Class<T> clazz;
    private final Method createMethod;

    public GenericObjectFactory(Class<T> clazz) {
        this.clazz = clazz;
        try {
            this.createMethod = clazz.getDeclaredMethod("createForPool");
            this.createMethod.setAccessible(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Missing static createForPool() method in " + clazz.getName(), e);
        }
    }

    @Override
    public PooledObject<T> makeObject() {
        try {
            @SuppressWarnings("unchecked")
            T instance = (T) createMethod.invoke(null);
            return new DefaultPooledObject<>(instance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create pooled object", e);
        }
    }

    @Override public void destroyObject(PooledObject<T> p) {}
    @Override public boolean validateObject(PooledObject<T> p) { return true; }
    @Override public void activateObject(PooledObject<T> p) {}
    @Override public void passivateObject(PooledObject<T> p) {}
}
