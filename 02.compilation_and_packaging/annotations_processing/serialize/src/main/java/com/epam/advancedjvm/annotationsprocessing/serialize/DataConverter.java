package com.epam.advancedjvm.annotationsprocessing.serialize;

public interface DataConverter<T> {

    byte[] toBytes(T data);

    T fromBytes(byte[] bytes);
}
