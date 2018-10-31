package com.epam.advancedjvm.microbenchmarks.examples;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class Conditional {

    @Param({"matching start", "matching end", "not matching"})
    private String lookup;

    @Benchmark
    public int mapValueIfElse() {
        if ("matching start".equals(lookup)) {
            return 0;
        } else if ("other 1".equals(lookup)) {
            return 1;
        } else if ("other 2".equals(lookup)) {
            return 2;
        } else if ("other 3".equals(lookup)) {
            return 3;
        } else if ("other 4".equals(lookup)) {
            return 4;
        } else if ("other 5".equals(lookup)) {
            return 5;
        } else if ("other 6".equals(lookup)) {
            return 6;
        } else if ("other 7".equals(lookup)) {
            return 7;
        } else if ("other 8".equals(lookup)) {
            return 8;
        } else if ("other 9".equals(lookup)) {
            return 9;
        } else if ("other 10".equals(lookup)) {
            return 10;
        } else if ("other 11".equals(lookup)) {
            return 11;
        } else if ("other 12".equals(lookup)) {
            return 12;
        } else if ("other 13".equals(lookup)) {
            return 13;
        } else if ("other 14".equals(lookup)) {
            return 14;
        } else if ("other 15".equals(lookup)) {
            return 15;
        } else if ("matching end".equals(lookup)) {
            return 100;
        }
        return Integer.MAX_VALUE;
    }

    @Benchmark
    public int mapValueSwitch() {
        switch (lookup) {
            case "matching start":
                return 0;
            case "other 1":
            return 1;
            case "other 2":
                return 2;
            case "other 3":
                return 3;
            case "other 4":
                return 4;
            case "other 5":
                return 5;
            case "other 6":
                return 6;
            case "other 7":
                return 7;
            case "other 8":
                return 8;
            case "other 9":
                return 9;
            case "other 10":
                return 10;
            case "other 11":
                return 11;
            case "other 12":
                return 12;
            case "other 13":
                return 13;
            case "other 14":
                return 14;
            case "other 15":
                return 15;
            case "matching end":
                return 100;

        }
        return Integer.MAX_VALUE;
    }
}
