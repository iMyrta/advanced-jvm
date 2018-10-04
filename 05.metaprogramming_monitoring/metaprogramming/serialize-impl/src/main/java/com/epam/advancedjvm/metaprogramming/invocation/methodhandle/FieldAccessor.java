package com.epam.advancedjvm.metaprogramming.invocation.methodhandle;

import java.lang.invoke.MethodHandle;

class FieldAccessor {

    final MethodHandle getter;
    final MethodHandle setter;

    FieldAccessor(MethodHandle getter, MethodHandle setter) {
        this.getter = getter;
        this.setter = setter;
    }
}
