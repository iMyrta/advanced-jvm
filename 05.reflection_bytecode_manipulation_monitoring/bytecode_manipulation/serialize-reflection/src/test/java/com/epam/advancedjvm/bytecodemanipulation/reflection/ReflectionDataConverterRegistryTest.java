package com.epam.advancedjvm.bytecodemanipulation.reflection;

import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverter;
import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverterRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectionDataConverterRegistryTest {

    @Test
    void doTest() {
        DummyData data = new DummyData();
        data.setField0(3);
        data.setField1("432");
        data.setField2("21");
        data.setField3(5);

        DataConverterRegistry registry = new ReflectionDataConverterRegistry();
        DataConverter<DummyData> converter = registry.getConverter(DummyData.class);
        DummyData actual = converter.fromBytes(converter.toBytes(data));

        Assertions.assertEquals(data, actual);
    }

}
