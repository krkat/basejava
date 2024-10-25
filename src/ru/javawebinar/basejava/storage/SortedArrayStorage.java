package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void sort() {
        for (int i = 0; i < size; i++) {
            Resume resumeToInsert = storage[i];
            int indexToInsert = (-1) * (Arrays.binarySearch(storage, 0, i, resumeToInsert) + 1);
            for (int j = i - 1; j >= indexToInsert; j--) {
                storage[j + 1] = storage[j];
            }
            storage[indexToInsert] = resumeToInsert;
        }
    }
}