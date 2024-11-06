package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void update(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
        storage.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume get(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void fillDeletedElement(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        if (uuid == null) return null;
        if (storage.containsKey(uuid)) return uuid;
        else return null;
    }

    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}