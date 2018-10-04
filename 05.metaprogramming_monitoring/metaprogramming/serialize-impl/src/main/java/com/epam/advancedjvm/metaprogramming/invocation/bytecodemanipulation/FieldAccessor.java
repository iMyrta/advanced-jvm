package com.epam.advancedjvm.metaprogramming.invocation.bytecodemanipulation;

import com.esotericsoftware.reflectasm.MethodAccess;

class FieldAccessor {

    final MethodAccess access;
    final int getter;
    final int setter;

    FieldAccessor(MethodAccess access, int getter, int setter) {
        this.access = access;
        this.getter = getter;
        this.setter = setter;
    }
}
