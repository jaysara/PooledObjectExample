# PooledObjectExample
# PooledObjectExample

This is a simple Java Maven project demonstrating **Object Pooling** with lifecycle enforcement using:

- Custom `@Pooled` annotation
- Private constructors to prevent direct instantiation
- Apache Commons Pool 2 for pooling implementation
- Reflection-based factory to create pooled objects only via the pool

## Features

- Enforces object creation only through the pool (no direct `new` calls)
- Generic reusable pool factory for any class annotated with `@Pooled`
- Easy integration with Apache Commons Pool 2
- Thread-safe object pooling

## How to Build and Run

Make sure you have Maven installed.

```bash
mvn clean compile exec:java -Dexec.mainClass="com.example.pool.Main"

