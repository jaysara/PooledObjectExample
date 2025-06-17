package com.example.pool;

public class Main {
    public static void main(String[] args) throws Exception {
        PooledFactory.registerFactory(ExpensiveResource.class, new ExpensiveResourceFactory());

        ExpensiveResource resource = PooledFactory.get(ExpensiveResource.class);
        resource.use();

        System.out.println("Available ExpensiveResource in pool: " + PooledFactory.availableCount(ExpensiveResource.class));
        System.out.println("Available NestedResource in pool: " + PooledFactory.availableCount(NestedResource.class));

        PooledFactory.release(ExpensiveResource.class, resource);
    }
}
