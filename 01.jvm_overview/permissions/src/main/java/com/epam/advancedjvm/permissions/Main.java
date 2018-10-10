package com.epam.advancedjvm.permissions;

public class Main {

    public static void main(String[] args) {
        StringHolder holder = new StringHolder("abc");
        System.out.println(holder);
	    StringHolderHack.hack(holder);
        System.out.println(holder);
    }
}
