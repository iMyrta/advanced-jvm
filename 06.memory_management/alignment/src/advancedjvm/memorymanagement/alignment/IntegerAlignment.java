package advancedjvm.memorymanagement.alignment;

public class IntegerAlignment {
	private final static int ARRAY_SIZE = 1000000;
	
	class Foo {
        int bar;
    }

    public IntegerAlignment() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        Foo[] foos = new Foo[ARRAY_SIZE];
        for (int i = 0; i < foos.length; i++) {
            foos[i] = new Foo();
        }
        System.out.println(this.getClass().getSimpleName() + ": " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " mb");
    }
}
