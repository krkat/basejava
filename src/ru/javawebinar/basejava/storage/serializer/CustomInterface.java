package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface CustomInterface {
    void read() throws IOException;
}
