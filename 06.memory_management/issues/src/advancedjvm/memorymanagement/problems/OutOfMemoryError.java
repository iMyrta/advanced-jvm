package advancedjvm.memorymanagement.problems;

import java.util.HashMap;
import java.util.Map;

public class OutOfMemoryError {

	private static Map<String, String> map = new HashMap<>();
	private static int[] array = new int[1000000];
	
	public static void main(String[] args) throws InterruptedException {
//		bigArray();
		for (int i = 0; i < 1000000; i++) {
			map.put(i + "", i + "");
			array[i] = i;
			Thread.sleep(10);
		}
	}

	public static void bigArray() {
		int[] x = new int[1000000000];
	}
}
