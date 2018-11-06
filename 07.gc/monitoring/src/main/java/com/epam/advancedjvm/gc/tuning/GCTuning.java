package com.epam.advancedjvm.gc.tuning;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 1. Run the GCTuning class with -XX:+UseParallelOldGC -Xmx1g to get a baseline speed.
 * Check the logs for full GCs (-Xloggc:gcLog.vgc).
 * See if you can reduce the full GCs by setting the "New Ratio" (-XX:NewRatio=n)
 * or the "Survivor Ratio" (-XX:SurvivorRatio=n).
 * See also what happens when you set the maximum GC pause time lower or higher
 * (-XX:MaxGCPauseMillis=num).
 *
 * 2. Run it with -XX:+UseConcMarkSweepGC -Xmx1g
 * Again, try improve by setting the new and survivor ratios
 *
 * 3. Run it with -XX:+UseG1GC -Xmx1g
 *
 * 4. Run it with -Xmx4g
 */

public class GCTuning {

    private static final int REPEATS = 1000000;
    private volatile static int MEMORY = 1 << 16;
    private static final int MIN_ARRAY_SIZE = 1000;
    private static final int MAX_ARRAY_SIZE = 5000;
    private static byte[][] memory = new byte[MEMORY][];
    private static int[] randomSizes =
            ThreadLocalRandom.current().
                    ints(MEMORY, MIN_ARRAY_SIZE, MAX_ARRAY_SIZE).
                    parallel().toArray();

    public static void main(String[] args) {
        long bestTime = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            bestTime = Math.min(test(), bestTime);
        }
        System.out.println("bestTime = " + bestTime);
    }

    private static long test() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < REPEATS; i++) {
            int mask = MEMORY - 1;
            int pos = i & mask;
            memory[pos] = new byte[randomSizes[pos]];
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
        return time;
    }

}
