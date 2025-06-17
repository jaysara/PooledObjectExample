package com.example.pool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PooledFactoryTest {

    @Test
    public void testRegisterAndGetRelease() throws Exception {
        PooledFactory.registerFactory(ExpensiveResource.class, new ExpensiveResourceFactory());

        ExpensiveResource resource1 = PooledFactory.get(ExpensiveResource.class);
        assertNotNull(resource1);
        assertNotNull(resource1.getNested());

        int beforeReturn = PooledFactory.availableCount(ExpensiveResource.class);
        PooledFactory.release(ExpensiveResource.class, resource1);
        int afterReturn = PooledFactory.availableCount(ExpensiveResource.class);
        assertEquals(beforeReturn + 1, afterReturn);
    }

    @Test
    public void testExceptionIfNoAnnotation() {
        class NoAnnotationClass {}

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PooledFactory.get(NoAnnotationClass.class);
        });

        assertTrue(exception.getMessage().contains("must be annotated with @Pooled"));
    }
}
