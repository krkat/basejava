package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    /*@Override
    protected void fillDeletedElement(Object searchKey) {
        int numMoved = size - (int) searchKey - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, (int) searchKey + 1, storage, (int) searchKey, numMoved);
        }
        super.fillDeletedElement(searchKey);
    }*/

    /*@Override
    protected void insertElement(Resume r, Object searchKey) {
//      http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        super.insertElement(r, (int) searchKey);
        int insertIdx = - (int) searchKey - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = r;
        size++;
    }*/

    @Override
    protected int getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insert(Resume r, Object searchKey) {
        int insertIdx = - (int) searchKey - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = r;
    }

    @Override
    protected void delete(Object searchKey) {
        int numMoved = size - (int) searchKey - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, (int) searchKey + 1, storage, (int) searchKey, numMoved);
        }
    }
}