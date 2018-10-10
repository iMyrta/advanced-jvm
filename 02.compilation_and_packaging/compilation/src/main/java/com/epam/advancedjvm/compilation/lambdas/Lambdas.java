package com.epam.advancedjvm.compilation.lambdas;

public class Lambdas {

    private static int staticField = 6;

    public static void main(String[] args) {
        Lambdas lambdas = new Lambdas();
        lambdas.callPrintInstanceCapturingThis();
        lambdas.callPrintInstanceCapturingLocal();
        lambdas.callPrintInstanceCapturingStatic();
        lambdas.callPrintInstanceNonCapturing();
        lambdas.callPrintStatic();
    }

    private void callPrintInstanceCapturingThis() {
        Runnable runnable = () -> printThisInstance();
        runnable.run();
    }

    private void callPrintInstanceCapturingLocal() {
        int local = 5;
        Runnable runnable = () -> printInstanceCapturing(local);
        runnable.run();
    }

    private void callPrintInstanceCapturingStatic() {
        Runnable runnable = () -> printInstanceCapturing(staticField);
        runnable.run();
    }

    private void callPrintInstanceNonCapturing() {
        Runnable runnable = () -> printInstance();
        runnable.run();
    }

    private void callPrintStatic() {
        Runnable staticRunnable = () -> printStatic();
        staticRunnable.run();
    }

    private void printInstance() {
        System.out.println("Instance method");
    }

    private void printInstanceCapturing(int capture) {
        System.out.println("Instance capturing " + capture + " method");
    }

    private void printThisInstance() {
        System.out.println("Instance " + this + " method");
    }

    private static void printStatic() {
        System.out.println("Static method");
    }

}
