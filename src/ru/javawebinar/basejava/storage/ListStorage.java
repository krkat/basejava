package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void update(Object searchKey, Resume r) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume get(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void fillDeletedElement(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected int getSearchKey(String uuid) {
        if (uuid == null) return -1;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}