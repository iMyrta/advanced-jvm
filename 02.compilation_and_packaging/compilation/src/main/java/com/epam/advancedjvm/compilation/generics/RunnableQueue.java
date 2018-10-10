package com.epam.advancedjvm.compilation.generics;

import java.util.LinkedList;

/**
 * Use javap -c or IntelliJ bytecode viewer
 */
public class RunnableQueue extends LinkedList<Runnable> {

    @Override
    public boolean offer(Runnable runnable) {
        return super.offer(runnable);
    }
}
