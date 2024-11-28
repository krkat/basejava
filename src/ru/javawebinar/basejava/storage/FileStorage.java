package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {
    private SerializationStrategy serializationStrategy;

    protected FileStorage(File directory, SerializationStrategy serializationStrategy) {
        super(directory);
        this.serializationStrategy = serializationStrategy;
    }

    public SerializationStrategy getSerializationStrategy() {
        return serializationStrategy;
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        serializationStrategy.doWrite(r, os);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return serializationStrategy.doRead(is);
    }
}