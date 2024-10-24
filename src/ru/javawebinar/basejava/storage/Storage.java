package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public interface Storage {

    void clear();

    boolean update(Resume r);

    boolean save(Resume r);

    Resume get(String uuid);

    boolean delete(String uuid);

    Resume[] getAll();

    int size();
}