package com.epam.advancedjvm.bytecodemanipulation.invocation;

public class Data {

    private int x;

    private String y;

    Data(int x, String y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
