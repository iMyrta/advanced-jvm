package com.epam.advancedjvm.microbenchmarks.pitfalls;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class LoopOptimization {

    @Param("5")
    private int x;

    @Benchmark
    public double log() {
        return x * x * x;
    }

    @Benchmark
    public double logLoop() {
        double result = 0;
        for (int i = 0; i < 100; i++) {
            result = x * x * x;
        }
        return result;
    }
}
