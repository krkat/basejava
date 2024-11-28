package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public interface SerializationStrategy {
    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}