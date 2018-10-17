package com.epam.advancedjvm.microbenchmarks.examples;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Warmup(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(2)
public class Fibonacci {

    @Param({"1", "5", "10", "20"})
    private int number;

    @Benchmark
    public int callRecursive() {
        return fibonacciRecursive(number);
    }

    @Benchmark
    public int callTailRecursive() {
        return fibonacciTailRecursive(number, 0, 1);
    }

    @Benchmark
    public int callIterative() {
        return fibonacciIterative(number);
    }

    private int fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    private int fibonacciIterative(int n) {
        if (n <= 1) {
            return n;
        }
        int fib = 1;
        int prevFib = 1;

        for (int i = 2; i < n; i++) {
            int temp = fib;
            fib += prevFib;
            prevFib = temp;
        }
        return fib;
    }

    private int fibonacciTailRecursive(int n, int a, int b) {
        if (n == 0) {
            return a;
        }
        if (n == 1) {
            return b;
        }
        return fibonacciTailRecursive(n - 1, b, a + b);
    }
}
