package com.epam.advancedjvm.microbenchmarks.examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(5)
public class Backoff {

    @Param({"0", "10", "20", "30", "40", "50"})
    private int tokens;

    private int plainInteger;
    private volatile int volatileInteger;

    @Benchmark
    public int incrPlain() {
        Blackhole.consumeCPU(tokens);
        return plainInteger++;
    }

    @Benchmark
    public int incrVolatile() {
        Blackhole.consumeCPU(tokens);
        return volatileInteger++;
    }

}
