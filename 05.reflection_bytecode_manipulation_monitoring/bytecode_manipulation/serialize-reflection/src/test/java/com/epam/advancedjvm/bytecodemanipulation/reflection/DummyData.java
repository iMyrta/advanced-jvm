package com.epam.advancedjvm.bytecodemanipulation.reflection;

import com.epam.advancedjvm.annotationsprocessing.serialize.Data;

import java.util.Objects;

@Data
public class DummyData {

    private int field0;
    private String field1;
    private String field2;
    private int field3;

    public int getField0() {
        return field0;
    }

    public void setField0(int field0) {
        this.field0 = field0;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DummyData dummyData = (DummyData) o;
        return field0 == dummyData.field0 &&
               field3 == dummyData.field3 &&
               Objects.equals(field1, dummyData.field1) &&
               Objects.equals(field2, dummyData.field2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field0, field1, field2, field3);
    }

    @Override
    public String toString() {
        return "DummyData{" +
               "field0=" + field0 +
               ", field1='" + field1 + '\'' +
               ", field2='" + field2 + '\'' +
               ", field3=" + field3 +
               '}';
    }
}
