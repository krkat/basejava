package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        storage.replace(searchKey, r);
    }

    @Override
    protected void doSave(Resume r, String uuid) {
        storage.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(String uuid) {
        return storage.containsKey(uuid);
    }
}