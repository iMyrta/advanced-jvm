package com.epam.advancedjvm.metaprogramming.serialize;

public interface DataConverter<T> {

    byte[] toBytes(T data);

    T fromBytes(byte[] bytes);
}
