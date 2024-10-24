package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public boolean update(Resume r) {
        if (super.update(r)) {
            sort();
            return true;
        }
        return false;
    }

    @Override
    public boolean save(Resume r) {
        if (super.save(r)) {
            sort();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String uuid) {
        if (super.delete(uuid)) {
            sort();
            return true;
        }
        return false;
    }

    private void sort() {
        for (int i = 0; i < size; i++) {
            Resume resumeToInsert = storage[i];
            int indexToInsert = (-1) * (Arrays.binarySearch(storage, 0, i, resumeToInsert) + 1);
            for (int j = i - 1; j >= indexToInsert; j--) {
                storage[j + 1] = storage[j];
            }
            storage[indexToInsert] = resumeToInsert;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}