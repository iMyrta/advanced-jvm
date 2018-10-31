package com.epam.advancedjvm.microbenchmarks.examples;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class ToArray {

    @Param({"32", "65536"})
    private int size;

    private List<Integer> list = new ArrayList<>();

    @Setup(Level.Trial)
    public void setup() {
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    }

    @Benchmark
    public Object[] toArray() {
        return list.toArray();
    }

    @Benchmark
    public Object[] toArraySized() {
        return list.toArray(new Object[list.size()]);
    }

    @Benchmark
    public Object[] toArrayUnSized() {
        return list.toArray(new Object[0]);
    }

    @Benchmark
    public Integer[] toIntegerArraySized() {
        return list.toArray(new Integer[list.size()]);
    }

    @Benchmark
    public Integer[] toIntegerArrayUnSized() {
        return list.toArray(new Integer[0]);
    }
}
