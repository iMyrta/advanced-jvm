package com.epam.advancedjvm.metaprogramming.invocation.reflection;

import java.lang.reflect.Method;

class FieldAccessor {

    final Method getter;
    final Method setter;

    FieldAccessor(Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;
    }
}
