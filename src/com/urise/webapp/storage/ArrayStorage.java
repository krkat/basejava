package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        if (size > 0) {
            Arrays.fill(storage, 0, size, null);
            size = 0;
        }
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("Ошибка! Хранилище переполнено.");
            return;
        }
        if (find(r.uuid) >= 0) {
            System.out.println("Ошибка! Резюме с таким uuid уже есть.");
            return;
        }
        storage[size] = r;
        size++;
    }

    public void update(String uuid, Resume r) {
        if (isPresent(uuid)) {
            storage[find(uuid)] = r;
        }
    }

    public Resume get(String uuid) {
        return isPresent(uuid) ? storage[find(uuid)] : null;
    }

    public void delete(String uuid) {
        if (isPresent(uuid)) {
            storage[find(uuid)] = storage[size - 1];
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

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isPresent(String uuid) {
        if (find(uuid) == -1) {
            System.out.println("Ошибка! Резюме с таким uuid нет.");
            return false;
        }
        return true;
    }
}