package com.epam.advancedjvm.processor;

public interface ProtobufConverter<T> {

    byte[] toBytes(T data);

    T fromBytes(byte[] bytes);
}
