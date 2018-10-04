package com.epam.advancedjvm.annotationsprocessing.processor;

class DataField {

    private final String name;
    private final String type;

    DataField(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
