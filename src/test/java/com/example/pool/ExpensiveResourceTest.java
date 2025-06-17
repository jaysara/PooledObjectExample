package com.example.pool;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpensiveResourceTest {

    @BeforeAll
    public static void setup() {
        PooledFactory.registerFactory(ExpensiveResource.class, new ExpensiveResourceFactory());
    }

    @Test
    public void testUse() throws Exception {
        ExpensiveResource resource = PooledFactory.get(ExpensiveResource.class);
        assertNotNull(resource);
        assertNotNull(resource.getNested());

        resource.use();

        PooledFactory.release(ExpensiveResource.class, resource);
    }
}
