package advancedjvm.memorymanagement.alignment;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class MemoryAlignment {

	
	public static void main(String[] args) {
		new BooleanAlignment();
		new IntegerAlignment();
		new DoubleAlignment();
		new FloatAlignment();
		new TwoFloatAlignment();
		
		printLineSeparator();
		System.out.println(VM.current().details());

		printLineSeparator();
		System.out.println(ClassLayout.parseClass(BooleanAlignment.Foo.class).toPrintable());

		printLineSeparator();
		System.out.println(ClassLayout.parseClass(IntegerAlignment.Foo.class).toPrintable());
		
		printLineSeparator();
		System.out.println(ClassLayout.parseClass(DoubleAlignment.Foo.class).toPrintable());
		
		printLineSeparator();
		System.out.println(ClassLayout.parseClass(FloatAlignment.Foo.class).toPrintable());
		
		printLineSeparator();
		System.out.println(ClassLayout.parseClass(TwoFloatAlignment.Foo.class).toPrintable());
	}

	private static void printLineSeparator() {
		System.out.println("\n" + "=========================================================" + "\n");
	}
}
