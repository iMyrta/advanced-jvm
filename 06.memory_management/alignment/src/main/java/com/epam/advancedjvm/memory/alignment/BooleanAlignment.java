package com.epam.advancedjvm.memory.alignment;

public class BooleanAlignment {
	private final static int ARRAY_SIZE = 1000000;

	static class Foo {
        byte a;
        int c;
        boolean d;
        long e;
        Object f;
    }

    public BooleanAlignment() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        Foo[] foos = new Foo[ARRAY_SIZE];
        for (int i = 0; i < foos.length; i++) {
            foos[i] = new Foo();
        }
        System.out.println(this.getClass().getSimpleName() + ": " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " mb");
    }
}
