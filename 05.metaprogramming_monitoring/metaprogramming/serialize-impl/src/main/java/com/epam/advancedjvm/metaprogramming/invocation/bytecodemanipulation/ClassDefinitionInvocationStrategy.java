package com.epam.advancedjvm.metaprogramming.invocation.bytecodemanipulation;

import com.epam.advancedjvm.metaprogramming.serialize.AbstractDataConverterRegistry.InvocationStrategy;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Field;

public class ClassDefinitionInvocationStrategy implements InvocationStrategy<FieldAccessor> {

    @Override
    public FieldAccessor createFieldAccessor(Field reflectionField, String getterName, String setterName) {
        MethodAccess access = MethodAccess.get(reflectionField.getDeclaringClass());
        return new FieldAccessor(
                access,
                access.getIndex(getterName, 0),
                access.getIndex(setterName, reflectionField.getType())
        );
    }

    @Override
    public Object invokeGetter(FieldAccessor field, Object instance) {
        return field.access.invoke(instance, field.getter);
    }

    @Override
    public void invokeSetter(FieldAccessor field, Object instance, Object value) {
        field.access.invoke(instance, field.setter, value);
    }
}
