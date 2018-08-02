package compilation;

public class InnerStaticClasses {

    public static int accessStaticClass(InnerStatic inner) {
        return inner.field1;
    }

    private static void staticMethod() {

    }


    private static class InnerStatic {
        private int field1;

        private InnerStatic() {
            staticMethod();
        }
    }
}
