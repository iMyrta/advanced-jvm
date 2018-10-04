package com.epam.advancedjvm.metaprogramming.serialize;

public interface DataConverterRegistry {

    <T> DataConverter<T> getConverter(Class<T> dataClass);
}
