package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface CustomConsumer<T, DataOutputStream>{
    void write(T t, DataOutputStream dataOutputStream) throws IOException;
}
