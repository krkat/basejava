package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.ObjectStreamSerializationStrategy;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}