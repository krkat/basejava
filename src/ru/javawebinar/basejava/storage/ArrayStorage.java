package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /*@Override
    protected void fillDeletedElement(Object searchKey) {
        storage[(int) searchKey] = storage[size - 1];
        super.fillDeletedElement(searchKey);
    }*/

    /*@Override
    protected void insertElement(Resume r, Object searchKey) {
        super.insertElement(r, (int) searchKey);
        storage[size] = r;
        size++;
    }*/

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insert(Resume r, Object searchKey) {
        storage[size] = r;
    }

    @Override
    protected void delete(Object searchKey) {
        storage[(int) searchKey] = storage[size - 1];
    }
}