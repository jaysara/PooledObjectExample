package com.example.pool;

@Pooled
public class ExpensiveResource {
    private NestedResource nested;

    ExpensiveResource() {}

    public void setNested(NestedResource nested) {
        this.nested = nested;
    }

    public NestedResource getNested() {
        return nested;
    }

    public void use() {
        System.out.println("ExpensiveResource using nested...");
        nested.doWork();
    }
}
