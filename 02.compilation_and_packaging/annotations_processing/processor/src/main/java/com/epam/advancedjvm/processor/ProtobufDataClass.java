package com.epam.advancedjvm.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class ProtobufDataClass {

    private final String packageName;
    private final String name;
    private final List<ProtobufDataField> fields;

    ProtobufDataClass(String packageName, String name, Collection<ProtobufDataField> fields) {
        this.packageName = packageName;
        this.name = name;
        this.fields = Collections.unmodifiableList(new ArrayList<>(fields));
    }

    String getPackageName() {
        return packageName;
    }

    String getName() {
        return name;
    }

    List<ProtobufDataField> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "ProtobufDataClass{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
