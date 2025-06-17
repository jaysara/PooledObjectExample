package com.example.pool;

@Pooled
public class ExpensiveResource {

    private ExpensiveResource() {
        System.out.println("ExpensiveResource created.");
    }

    public void use() {
        System.out.println("Using: " + this);
    }

    static ExpensiveResource createForPool() {
        return new ExpensiveResource();
    }
}
