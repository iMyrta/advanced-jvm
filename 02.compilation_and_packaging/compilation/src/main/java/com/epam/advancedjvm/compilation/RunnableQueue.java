package com.epam.advancedjvm.compilation;

import java.util.LinkedList;

public class RunnableQueue extends LinkedList<Runnable> {

    @Override
    public boolean offer(Runnable runnable) {
        return super.offer(runnable);
    }
}
