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
public class FindItem {

    //418 = 2 + 31 + 73 + 131 + 181
    //593 = 23 + 67 + 109 + 167 + 227
    @Param({"431", "593", "1"})
    private int number;

    private int[] primitiveArray;
    private Integer[] wrapperArray;
    private List<Integer> linkedList;
    private List<Integer> arrayList;

    @Setup(Level.Trial)
    public void setup() {
        int[] first = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        int[] second = {31, 37, 41, 43, 47, 53, 59, 61, 67, 71};
        int[] third = {73, 79, 83, 89, 97, 101, 103, 107, 109, 113};
        int[] fourth = {127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        int[] fifth = {179, 181, 191, 193, 197, 199, 211, 223, 227, 229};

        int size = first.length * second.length * third.length * fourth.length * fifth.length;
        primitiveArray = new int[size];
        wrapperArray = new Integer[size];
        linkedList = new LinkedList<>();
        arrayList = new ArrayList<>(size);

        int index = 0;
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {
                for (int k = 0; k < third.length; k++) {
                    for (int l = 0; l < fourth.length; l++) {
                        for (int m = 0; m < fifth.length; m++) {

                            int sum = first[i] + second[j] + third[k] + fourth[l] + fifth[m];
                            primitiveArray[index] = sum;
                            wrapperArray[index] = sum;
                            linkedList.add(sum);
                            arrayList.add(sum);
                            index++;
                        }
                    }
                }
            }
        }
    }

    @Benchmark
    public boolean findPrimitiveArray() {
        for (int current : primitiveArray) {
            if (current == number) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public boolean findWrapperArray() {
        for (Integer current : wrapperArray) {
            if (current == number) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public boolean findLinkedList() {
        return linkedList.indexOf(number) == -1;
    }

    @Benchmark
    public boolean findLinkedListIterator() {
        for (Integer current : linkedList) {
            if (current == number) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public boolean findArrayList() {
        return arrayList.indexOf(number) == -1;
    }

    @Benchmark
    public boolean findArrayListIterator() {
        for (Integer current : arrayList) {
            if (current == number) {
                return true;
            }
        }
        return false;
    }
}
