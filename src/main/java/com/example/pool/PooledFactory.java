package com.example.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PooledFactory {

    private static final Map<Class<?>, GenericObjectPool<?>> pools = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Pooled.class)) {
            throw new IllegalArgumentException(clazz + " must be annotated with @Pooled");
        }

        return (T) pools.computeIfAbsent(clazz, clz ->
                new GenericObjectPool<>(new GenericObjectFactory<>(clazz))
        ).borrowObject();
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
