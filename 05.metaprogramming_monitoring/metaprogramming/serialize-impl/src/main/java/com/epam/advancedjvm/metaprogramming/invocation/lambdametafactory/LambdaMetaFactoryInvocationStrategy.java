package com.epam.advancedjvm.metaprogramming.invocation.lambdametafactory;

import com.epam.advancedjvm.metaprogramming.serialize.DataConverterRegistryImpl.InvocationStrategy;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public class LambdaMetaFactoryInvocationStrategy implements InvocationStrategy<FieldAccessor> {

    private static final Lookup LOOKUP = MethodHandles.lookup();

    @Override
    public FieldAccessor createFieldAccessor(Field reflectionField, String getterName, String setterName) throws Throwable {
        Class<?> dataClass = reflectionField.getDeclaringClass();
        return new FieldAccessor(
                extractGetter(dataClass, reflectionField, getterName),
                extractSetter(dataClass, reflectionField, setterName)
        );
    }

    @Override
    public Object invokeGetter(FieldAccessor field, Object instance) {
        return field.getter.apply(instance);
    }

    @Override
    public void invokeSetter(FieldAccessor field, Object instance, Object value) {
        field.setter.accept(instance, value);
    }

    @SuppressWarnings("unchecked")
    private static Function<Object, Object> extractGetter(Class<?> dataClass, Field reflectionField, String getterName) throws Throwable {
        MethodHandle target = LOOKUP.findVirtual(dataClass, getterName, MethodType.methodType(reflectionField.getType()));

        MethodType getterFunction = target.type();
        CallSite site = LambdaMetafactory.metafactory(LOOKUP,
                "apply",
                //we can optimize to use primitives here if they were supported by protobuf dynamic message
                MethodType.methodType(Function.class),
                //we need to bind to the erasure of the dataClass and the wrapper of primitive fields
                getterFunction.generic(),
                target,
                getterFunction
        );
        return (Function) site.getTarget().invoke();
    }

    @SuppressWarnings("unchecked")
    private static BiConsumer<Object, Object> extractSetter(Class<?> dataClass, Field reflectionField, String setterName) throws Throwable {
        MethodHandle target = LOOKUP.findVirtual(dataClass, setterName, MethodType.methodType(void.class, reflectionField.getType()));

        MethodType setterConsumer = target.type();
        CallSite site = LambdaMetafactory.metafactory(LOOKUP,
                "accept",
                //we can optimize to use primitives here if they were supported by protobuf dynamic message
                MethodType.methodType(BiConsumer.class),
                //except the return type we need to bind to the erasure of the dataClass and the wrapper of primitive fields
                MethodType.methodType(setterConsumer.returnType(), setterConsumer.generic().parameterArray()),
                target,
                setterConsumer
        );
        return (BiConsumer) site.getTarget().invoke();
    }
}
