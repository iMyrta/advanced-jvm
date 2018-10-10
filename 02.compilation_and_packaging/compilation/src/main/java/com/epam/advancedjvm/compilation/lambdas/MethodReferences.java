package com.epam.advancedjvm.compilation.lambdas;

import java.util.function.Consumer;

public class MethodReferences {

    public static void main(String[] args) {
        callPrintInstanceCapturing();
        callPrintInstanceNonCapturing();
        callPrintThisInstanceCapturing();
        callPrintThisInstanceNonCapturing();
        callPrintStatic();
    }

    private static void callPrintInstanceCapturing() {
        MethodReferences instance = new MethodReferences();
        Runnable runnable = instance::printInstance;
        runnable.run();
    }

    private static void callPrintInstanceNonCapturing() {
        Consumer<MethodReferences> consumer = MethodReferences::printInstance;
        consumer.accept(new MethodReferences());
    }

    private static void callPrintThisInstanceCapturing() {
        MethodReferences instance = new MethodReferences();
        Runnable runnable = instance::printThisInstance;
        runnable.run();
    }

    private static void callPrintThisInstanceNonCapturing() {
        Consumer<MethodReferences> consumer = MethodReferences::printThisInstance;
        consumer.accept(new MethodReferences());
    }

    private static void callPrintStatic() {
        Runnable runnable = MethodReferences::printStatic;
        runnable.run();
    }

    private void printInstance() {
        System.out.println("Instance method");
    }

    private void printThisInstance() {
        System.out.println("Instance " + this + " method");
    }

    private static void printStatic() {
        System.out.println("Static method");
    }

}
