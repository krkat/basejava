package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(Resume r, Object searchKey) {
        int insertIdx = - (int) searchKey - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = r;
    }

    @Override
    protected void fillDeletedElement(Object searchKey) {
        int numMoved = size - (int) searchKey - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, (int) searchKey + 1, storage, (int) searchKey, numMoved);
        }
    }
}