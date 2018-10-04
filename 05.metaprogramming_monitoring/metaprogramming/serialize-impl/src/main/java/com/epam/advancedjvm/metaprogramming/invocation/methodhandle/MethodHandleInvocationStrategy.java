package com.epam.advancedjvm.metaprogramming.invocation.methodhandle;

import com.epam.advancedjvm.metaprogramming.serialize.AbstractDataConverterRegistry.InvocationStrategy;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class MethodHandleInvocationStrategy implements InvocationStrategy<FieldAccessor> {

    private static final Lookup LOOKUP = MethodHandles.lookup();

    @Override
    public FieldAccessor createFieldAccessor(Field reflectionField, String getterName, String setterName) throws Exception {
        Class<?> dataClass = reflectionField.getDeclaringClass();
        return new FieldAccessor(
                LOOKUP.findVirtual(dataClass, getterName, MethodType.methodType(reflectionField.getType())),
                LOOKUP.findVirtual(dataClass, setterName, MethodType.methodType(void.class, reflectionField.getType()))
        );
    }

    @Override
    public Object invokeGetter(FieldAccessor field, Object instance) throws Throwable {
        return field.getter.invoke(instance);
    }

    @Override
    public void invokeSetter(FieldAccessor field, Object instance, Object value) throws Throwable {
        field.setter.invoke(instance, value);
    }
}
