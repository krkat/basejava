package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface CustomConsumer<T>{
    void write(T t) throws IOException;
}
