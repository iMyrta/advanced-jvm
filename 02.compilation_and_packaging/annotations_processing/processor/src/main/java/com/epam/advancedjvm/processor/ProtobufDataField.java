package com.epam.advancedjvm.processor;

class ProtobufDataField {

    private final String name;
    private final String type;

    ProtobufDataField(String name,String type) {
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
