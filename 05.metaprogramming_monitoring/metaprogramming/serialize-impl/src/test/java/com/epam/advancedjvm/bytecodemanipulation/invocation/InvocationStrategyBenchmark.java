package com.epam.advancedjvm.bytecodemanipulation.invocation;

import com.epam.advancedjvm.metaprogramming.invocation.bytecodemanipulation.ClassDefinitionInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.invocation.lambdametafactory.LambdaMetaFactoryInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.invocation.methodhandle.MethodHandleInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.invocation.reflection.ReflectionInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.serialize.AbstractDataConverterRegistry.InvocationStrategy;
import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 15, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
public class InvocationStrategyBenchmark {

    private Data data;

    private ReflectionInvocationStrategy reflectionInvocationStrategy;
    private Object reflectionPrimitiveGetter;
    private Object reflectionReferenceGetter;

    private MethodHandleInvocationStrategy methodHandleInvocationStrategy;
    private Object methodHandlePrimitiveGetter;
    private Object methodHandleReferenceGetter;

    private LambdaMetaFactoryInvocationStrategy lambdaMetaFactoryInvocationStrategy;
    private Object lambdaMetaFactoryPrimitiveGetter;
    private Object lambdaMetaFactoryReferenceGetter;

    private ClassDefinitionInvocationStrategy classDefinitionInvocationStrategy;
    private Object classDefinitionPrimitiveGetter;
    private Object classDefinitionReferenceGetter;

    @Setup
    public void setup() throws Throwable {
        data = new Data(123, "456");
        Field primitive = Data.class.getDeclaredField("x");
        Field reference = Data.class.getDeclaredField("y");

        reflectionInvocationStrategy = new ReflectionInvocationStrategy();
        reflectionPrimitiveGetter = reflectionInvocationStrategy.createFieldAccessor(primitive, "getX", "setX");
        reflectionReferenceGetter = reflectionInvocationStrategy.createFieldAccessor(reference, "getY", "setY");

        methodHandleInvocationStrategy = new MethodHandleInvocationStrategy();
        methodHandlePrimitiveGetter = methodHandleInvocationStrategy.createFieldAccessor(primitive, "getX", "setX");
        methodHandleReferenceGetter = methodHandleInvocationStrategy.createFieldAccessor(reference, "getY", "setY");

        lambdaMetaFactoryInvocationStrategy = new LambdaMetaFactoryInvocationStrategy();
        lambdaMetaFactoryPrimitiveGetter = lambdaMetaFactoryInvocationStrategy.createFieldAccessor(primitive, "getX", "setX");
        lambdaMetaFactoryReferenceGetter = lambdaMetaFactoryInvocationStrategy.createFieldAccessor(reference, "getY", "setY");

        classDefinitionInvocationStrategy = new ClassDefinitionInvocationStrategy();
        classDefinitionPrimitiveGetter = classDefinitionInvocationStrategy.createFieldAccessor(primitive, "getX", "setX");
        classDefinitionReferenceGetter = classDefinitionInvocationStrategy.createFieldAccessor(reference, "getY", "setY");
    }

    @Benchmark
    public Object baseLinePrimitive() {
        return data.getX();
    }

    @Benchmark
    public Object reflectionPrimitiveGetter() throws Throwable {
        return invokeGetter(reflectionInvocationStrategy, reflectionPrimitiveGetter, data);
    }

    @Benchmark
    public Object methodHandlePrimitiveGetter() throws Throwable {
        return invokeGetter(methodHandleInvocationStrategy, methodHandlePrimitiveGetter, data);
    }

    @Benchmark
    public Object lambdaMetaFactoryPrimitiveGetter() throws Throwable {
        return invokeGetter(lambdaMetaFactoryInvocationStrategy, lambdaMetaFactoryPrimitiveGetter, data);
    }

    @Benchmark
    public Object classDefinitionPrimitiveGetter() throws Throwable {
        return invokeGetter(classDefinitionInvocationStrategy, classDefinitionPrimitiveGetter, data);
    }

    @Benchmark
    public Object baseLineReference() {
        return data.getY();
    }

    @Benchmark
    public Object reflectionReferenceGetter() throws Throwable {
        return invokeGetter(reflectionInvocationStrategy, reflectionReferenceGetter, data);
    }

    @Benchmark
    public Object methodHandleReferenceGetter() throws Throwable {
        return invokeGetter(methodHandleInvocationStrategy, methodHandleReferenceGetter, data);
    }

    @Benchmark
    public Object lambdaMetaFactoryReferenceGetter() throws Throwable {
        return invokeGetter(lambdaMetaFactoryInvocationStrategy, lambdaMetaFactoryReferenceGetter, data);
    }

    @Benchmark
    public Object classDefinitionReferenceGetter() throws Throwable {
        return invokeGetter(classDefinitionInvocationStrategy, classDefinitionReferenceGetter, data);
    }

    @SuppressWarnings("unchecked")
    private Object invokeGetter(InvocationStrategy invocationStrategy, Object field, Data data) throws Throwable {
        return invocationStrategy.invokeGetter(field, data);
    }
}
