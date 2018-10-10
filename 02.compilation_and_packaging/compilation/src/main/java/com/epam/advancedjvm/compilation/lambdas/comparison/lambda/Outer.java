package com.epam.advancedjvm.compilation.lambdas.comparison.lambda;

import java.util.concurrent.ExecutorService;

public class Outer {

    public void submit(ExecutorService executor) {
        executor.submit(() -> System.out.println("Called from " + this));
    }
}
