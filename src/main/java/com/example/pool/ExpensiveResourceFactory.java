package com.example.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ExpensiveResourceFactory implements PooledObjectFactory<ExpensiveResource> {

    @Override
    public PooledObject<ExpensiveResource> makeObject() throws Exception {
        ExpensiveResource resource = new ExpensiveResource();
        NestedResource nested = PooledFactory.get(NestedResource.class);
        resource.setNested(nested);
        System.out.println("ExpensiveResource created with nested.");
        return new DefaultPooledObject<>(resource);
    }

    @Override
    public void destroyObject(PooledObject<ExpensiveResource> p) throws Exception {
        PooledFactory.release(NestedResource.class, p.getObject().getNested());
    }

    @Override
    public boolean validateObject(PooledObject<ExpensiveResource> p) {
        return p.getObject() != null && p.getObject().getNested() != null;
    }

    @Override public void activateObject(PooledObject<ExpensiveResource> p) {}

    @Override public void passivateObject(PooledObject<ExpensiveResource> p) {}
}
