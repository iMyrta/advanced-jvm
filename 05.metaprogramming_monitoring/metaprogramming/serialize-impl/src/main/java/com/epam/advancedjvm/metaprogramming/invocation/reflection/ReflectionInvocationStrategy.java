package com.epam.advancedjvm.metaprogramming.invocation.reflection;

import com.epam.advancedjvm.metaprogramming.serialize.AbstractDataConverterRegistry.InvocationStrategy;

import java.lang.reflect.Field;

public class ReflectionInvocationStrategy implements InvocationStrategy<FieldAccessor> {

    @Override
    public FieldAccessor createFieldAccessor(Field reflectionField, String getterName, String setterName) throws Exception {
        Class<?> dataClass = reflectionField.getDeclaringClass();
        return new FieldAccessor(
                dataClass.getDeclaredMethod(getterName),
                dataClass.getDeclaredMethod(setterName, reflectionField.getType())
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
