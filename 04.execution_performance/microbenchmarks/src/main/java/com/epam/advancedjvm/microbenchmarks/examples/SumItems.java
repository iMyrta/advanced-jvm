package com.epam.advancedjvm.microbenchmarks.examples;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class SumItems {

    private int[] primitiveArray;
    private Integer[] wrapperArray;
    private List<Integer> linkedList;
    private List<Integer> arrayList;

    @Setup(Level.Trial)
    public void setup() {
        int[] first = {2, 3, 5, 7, 11, 13};
        int[] second = {17, 19, 23, 29, 31, 37};

        int size = first.length * second.length;
        primitiveArray = new int[size];
        wrapperArray = new Integer[size];
        linkedList = new LinkedList<>();
        arrayList = new ArrayList<>(size);

        int index = 0;
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {

                int product = first[i] * second[j];
                primitiveArray[index] = product;
                wrapperArray[index] = product;
                linkedList.add(product);
                arrayList.add(product);
                index++;
            }
        }
    }

    @Benchmark
    public int primitiveArray() {
        int sum = 0;
        for (int current : primitiveArray) {
            sum += current;
        }
        return sum;
    }

    @Benchmark
    public int wrapperArray() {
        int sum = 0;
        for (int current : wrapperArray) {
            sum += current;
        }
        return sum;
    }

    @Benchmark
    public int linkedListIterator() {
        int sum = 0;
        for (int current : linkedList) {
            sum += current;
        }
        return sum;
    }

    @Benchmark
    public int linkedListStream() {
        return linkedList.stream().mapToInt(Integer::intValue).sum();
    }

    @Benchmark
    public int arrayList() {
        int sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i);
        }
        return sum;
    }

    @Benchmark
    public int arrayListIterator() {
        int sum = 0;
        for (int current : arrayList) {
            sum += current;
        }
        return sum;
    }

    @Benchmark
    public int arrayListStream() {
        return arrayList.stream().mapToInt(Integer::intValue).sum();
    }
}

