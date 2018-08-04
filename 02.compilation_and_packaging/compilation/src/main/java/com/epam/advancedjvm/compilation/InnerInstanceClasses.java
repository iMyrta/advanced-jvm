package com.epam.advancedjvm.compilation;

public class InnerInstanceClasses {

    private Inner inner = new Inner("");


    private class Inner {

        Inner(String value) {
            System.out.println(InnerInstanceClasses.this);
        }
    }
}
