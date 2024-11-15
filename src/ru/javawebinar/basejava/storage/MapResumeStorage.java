package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected void doUpdate(Resume r, Object resume) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        storage.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return storage.get(((Resume) resume).getUuid());
    }

    @Override
    protected void doDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    protected boolean isExist(Object resume) {
        return resume != null;
    }
}