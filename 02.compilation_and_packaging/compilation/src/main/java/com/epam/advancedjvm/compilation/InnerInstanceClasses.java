package com.epam.advancedjvm.compilation;

/**
 * Use javap -c or IntelliJ bytecode viewer
 */
public class InnerInstanceClasses {

    private Inner inner = new Inner("");


    private class Inner {

        Inner(String value) {
            System.out.println(InnerInstanceClasses.this);
        }
    }
}
