package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class StrategyStorage implements Storage {
    private Map<StorageType, Storage> strategies = new EnumMap<>(StorageType.class);
    public Storage storage;
    private String dir;

    public StrategyStorage(StorageType type, String dir) {
        this.dir = dir;
        strategies.put(StorageType.FILE, new ObjectStreamStorage(new File(dir)));
        strategies.put(StorageType.PATH, new ObjectStreamPathStorage(dir));
        this.storage = strategies.get(type);
    }

    public void setStrategy(StorageType type) {
        storage = strategies.get(type);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        storage.update(r);
    }

    @Override
    public void save(Resume r) {
        storage.save(r);
    }

    @Override
    public Resume get(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        storage.delete(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.getAllSorted();
    }

    @Override
    public int size() {
        return storage.size();
    }
}