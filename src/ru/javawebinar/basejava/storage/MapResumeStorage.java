package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        storage.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return storage.get((resume).getUuid());
    }

    @Override
    protected void doDelete(Resume resume) {
        storage.remove((resume).getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    protected boolean isExist(Resume resume) {
        return resume != null;
    }
}