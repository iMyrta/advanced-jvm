package com.epam.advancedjvm.compilation.generics;

import java.util.Arrays;
import java.util.List;

/**
 * Use javap -c or IntelliJ bytecode viewer
 *
 * What if we restrict T to implement an interface?
 */
public class TypeErasure<T> {

    T[] data;


    public static void differentLists() {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        System.out.println(ints);
        List<String> strings = Arrays.asList("1", "2", "3");
        System.out.println(strings);
    }
}
