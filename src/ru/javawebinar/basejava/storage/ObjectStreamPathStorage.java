package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage implements SerializationStrategy {
    private SerializationStrategy serializationStrategy;

    protected ObjectStreamPathStorage(String directory, SerializationStrategy strategy) {
        super(directory);
        this.serializationStrategy = strategy;
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