package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        if (size > 0) {
            Arrays.fill(storage, 0, size, null);
            size = 0;
        }
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Ошибка! Хранилище переполнено.");
            return;
        }
        int index = findIndex(r.uuid);
        if (index >= 0) {
            System.out.println("Ошибка! Резюме с таким uuid уже есть.");
            return;
        }
        storage[size] = r;
        size++;
    }

    public void update(String uuid, Resume r) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        return isExisting(index) ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExisting(index)) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isExisting(int index) {
        if (index == -1) {
            System.out.println("Ошибка! Резюме с таким uuid нет.");
            return false;
        }
        return true;
    }
}