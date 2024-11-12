package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    protected void doSave(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume(r, (Integer) index);
        size++;
    }

    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    protected void doDelete(Object index) {
        fillDeletedElement((int) index);
        storage[size - 1] = null;
        size--;
    }

    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract Integer getSearchKey(String uuid);
}