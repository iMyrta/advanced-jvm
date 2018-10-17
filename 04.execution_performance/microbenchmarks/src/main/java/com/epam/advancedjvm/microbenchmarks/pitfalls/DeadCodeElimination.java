package com.epam.advancedjvm.microbenchmarks.pitfalls;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class DeadCodeElimination {

    @Benchmark
    public void baseLine() {

    }

    @Benchmark
    public void callMyMethod() {
        myMethod();
    }

    private int myMethod() {
        int sum = 0;
        for (int i = 0; i < 10_000; i++) {
            sum++;
        }
        return sum;
    }
}
