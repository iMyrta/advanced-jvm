package com.epam.advancedjvm.bytecodemanipulation.invocation;

import com.epam.advancedjvm.metaprogramming.invocation.bytecodemanipulation.ClassDefinitionInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.invocation.lambdametafactory.LambdaMetaFactoryInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.invocation.methodhandle.MethodHandleInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.invocation.reflection.ReflectionInvocationStrategy;
import com.epam.advancedjvm.metaprogramming.serialize.AbstractDataConverterRegistry.InvocationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

class InvocationStrategyTest {

    @ParameterizedTest
    @MethodSource("createStrategies")
    <F> void doTest(InvocationStrategy<F> strategy) throws Throwable {
        Data data = new Data(5, "7");
        String strategyClass = strategy.getClass().toString();

        Field field = Data.class.getDeclaredField("x");
        F accessor = strategy.createFieldAccessor(field, "getX", "setX");
        Assertions.assertEquals(5, strategy.invokeGetter(accessor, data), strategyClass);
        strategy.invokeSetter(accessor, data, 6);
        Assertions.assertEquals(6, data.getX(), strategyClass);

        field = Data.class.getDeclaredField("y");
        accessor = strategy.createFieldAccessor(field, "getY", "setY");
        Assertions.assertEquals("7", strategy.invokeGetter(accessor, data), strategyClass);
        strategy.invokeSetter(accessor, data, "8");
        Assertions.assertEquals("8", data.getY(), strategyClass);
    }

    private static List<InvocationStrategy<?>> createStrategies() {
        return Arrays.asList(
                new ReflectionInvocationStrategy(),
                new MethodHandleInvocationStrategy(),
                new LambdaMetaFactoryInvocationStrategy(),
                new ClassDefinitionInvocationStrategy()
        );
    }
}
