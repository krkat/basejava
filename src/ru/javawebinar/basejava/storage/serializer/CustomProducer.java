package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface CustomProducer<T> {
    T read() throws IOException;
}
