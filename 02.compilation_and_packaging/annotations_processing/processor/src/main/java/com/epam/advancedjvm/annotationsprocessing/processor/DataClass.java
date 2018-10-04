package com.epam.advancedjvm.annotationsprocessing.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class DataClass {

    private final String packageName;
    private final String name;
    private final List<DataField> fields;

    DataClass(String packageName, String name, Collection<DataField> fields) {
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

    List<DataField> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "DataClass{" +
               "packageName='" + packageName + '\'' +
               ", name='" + name + '\'' +
               ", fields=" + fields +
               '}';
    }
}
