package com.example.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PooledFactory {

    private static final Map<Class<?>, GenericObjectPool<?>> pools = new ConcurrentHashMap<>();
    private static final Map<Class<?>, PooledObjectFactory<?>> factoryRegistry = new ConcurrentHashMap<>();

    public static <T> void registerFactory(Class<T> clazz, PooledObjectFactory<T> factory) {
        factoryRegistry.put(clazz, factory);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Pooled.class)) {
            throw new IllegalArgumentException(clazz + " must be annotated with @Pooled");
        }

        GenericObjectPool<T> pool;
        synchronized (pools) {
            pool = (GenericObjectPool<T>) pools.get(clazz);
            if (pool == null) {
                PooledObjectFactory<T> factory = (PooledObjectFactory<T>) factoryRegistry.get(clazz);
                if (factory == null) {
                    factory = new GenericObjectFactory<>(clazz);
                }
                pool = new GenericObjectPool<>(factory);
                pools.put(clazz, pool);
            }
        }
        return pool.borrowObject();
    }


    @SuppressWarnings("unchecked")
    public static <T> void release(Class<T> clazz, T instance) {
        GenericObjectPool<T> pool = (GenericObjectPool<T>) pools.get(clazz);
        if (pool != null) {
            pool.returnObject(instance);
        }
    }

    public static int availableCount(Class<?> clazz) {
        GenericObjectPool<?> pool = pools.get(clazz);
        return pool != null ? pool.getNumIdle() : 0;
    }
}
