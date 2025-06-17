package com.example.pool;

public class Main {
    public static void main(String[] args) throws Exception {
        ExpensiveResource res1 = PooledFactory.get(ExpensiveResource.class);
        res1.use();
        ExpensiveResource res2 = PooledFactory.get(ExpensiveResource.class);
        res2.use();
        ExpensiveResource res3 = PooledFactory.get(ExpensiveResource.class);
        res3.use();


        System.out.println("Available in pool: " + PooledFactory.availableCount(ExpensiveResource.class));

        PooledFactory.release(ExpensiveResource.class, res1);
        System.out.println("Returned to pool.");
        System.out.println("Available in pool: " + PooledFactory.availableCount(ExpensiveResource.class));
        res2 = PooledFactory.get(ExpensiveResource.class);
        res2.use();
        System.out.println("Available in pool: " + PooledFactory.availableCount(ExpensiveResource.class));

    }
}

