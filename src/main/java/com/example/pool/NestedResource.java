package com.example.pool;

@Pooled
public class NestedResource {
    private NestedResource() {
        System.out.println("NestedResource created");
    }

    public void doWork() {
        System.out.println("NestedResource working: " + this);
    }

    static NestedResource createForPool() {
        return new NestedResource();
    }
}
