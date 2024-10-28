package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume r) {
        if (index == size) {
            index = getIndex(r.getUuid());
            index = index >= 0 ? index : (-1) * (index + 1);
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = r;
        } else {
            index = index >= 0 ? index : (-1) * (index + 1);
            storage[index] = r;
        }
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}