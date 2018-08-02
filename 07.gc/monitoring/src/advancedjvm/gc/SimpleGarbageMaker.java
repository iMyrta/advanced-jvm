package advancedjvm.gc;

import java.util.HashMap;

public class SimpleGarbageMaker {

	public static void main(String[] args) {
		System.out.println("Running Test Program");
		String stringDataPrefix = "AdvancedJVM GC test";
		{
			HashMap<String, String> stringMap = new HashMap<>();
			for (int i = 0; i < 5000000; ++i) {
				String newStringData = stringDataPrefix + " index_" + i;
				stringMap.put(newStringData, String.valueOf(i));
			}
			
			System.out.println("MAP size: " + stringMap.size());
			
			for (int i = 0; i < 4000000; ++i) {
				String newStringData = stringDataPrefix + " index_" + i;
				stringMap.remove(newStringData);
			}
			
			System.out.println("MAP size: " + stringMap.size());
			System.gc();
		}
	}

}
