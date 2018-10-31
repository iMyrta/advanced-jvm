package com.epam.advancedjvm.microbenchmarks.pitfalls;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class ConstantFolding {

    private double pi = Math.PI;

    @Benchmark
    public double baseLine() {
        return 2903677.27061;
    }

    @Benchmark
    public double pow() {
        return pow(Math.PI, 13);
    }

    @Benchmark
    public double powFixed() {
        return pow(pi, 13);
    }

    private double pow(double base, int exponent) {
        double result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

}
