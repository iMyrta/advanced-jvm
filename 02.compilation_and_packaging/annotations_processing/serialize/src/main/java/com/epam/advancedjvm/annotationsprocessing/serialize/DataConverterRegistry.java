package com.epam.advancedjvm.annotationsprocessing.serialize;

public interface DataConverterRegistry {

    <T> DataConverter<T> getConverter(Class<T> dataClass);

}
