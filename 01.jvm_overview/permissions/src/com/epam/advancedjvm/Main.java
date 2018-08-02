package com.epam.advancedjvm;

public class Main {

    public static void main(String[] args) {
        StringHolder holder = new StringHolder("abc");
	    StringHolderHack.hack(holder);
        System.out.println(holder);
    }
}
