package com.epam.advancedjvm.compilation.lambdas.comparison.anonymous;

import java.util.concurrent.ExecutorService;

public class Outer {

    public void submit(ExecutorService executor) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Called from " + Outer.this);
            }
        });
    }
}
