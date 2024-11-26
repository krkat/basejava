package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamStorage implements Strategy {
    public Strategy strategy;

    public ObjectStreamStorage(Strategy Strategy) {
        this.strategy = Strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        strategy.doWrite(r, os);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return strategy.doRead(is);
    }
}