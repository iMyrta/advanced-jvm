package com.epam.advancedjvm.metaprogramming.invocation.lambdametafactory;

import java.util.function.BiConsumer;
import java.util.function.Function;

class FieldAccessor {

    final Function<Object, Object> getter;
    final BiConsumer<Object, Object> setter;

    FieldAccessor(Function<Object, Object> getter, BiConsumer<Object, Object> setter) {
        this.getter = getter;
        this.setter = setter;
    }
}
