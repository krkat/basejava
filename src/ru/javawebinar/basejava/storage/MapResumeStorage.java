package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.replace(((Resume) searchKey).getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}