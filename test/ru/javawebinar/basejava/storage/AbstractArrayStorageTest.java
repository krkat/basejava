package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        Resume[] emptyStorage = new Resume[0];
        storage.clear();
        Assert.assertArrayEquals(emptyStorage, storage.getAll());
    }

    @Test
    public void update() throws Exception {
        Resume updated = new Resume(UUID_1);
        storage.update(updated);
        Assert.assertEquals(updated, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = new Resume[3];
        expected[0] = new Resume(UUID_1);
        expected[1] = new Resume(UUID_2);
        expected[2] = new Resume(UUID_3);
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume saved = new Resume("uuid4");
        storage.save(saved);
        Assert.assertEquals(saved, storage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        storage.get(UUID_1);
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume(UUID_1);
        Assert.assertEquals(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.valueOf(i)));
            }
        } catch (StorageException e) {
            Assert.fail("Storage overflow ahead of time.");
        }
        storage.save(new Resume(String.valueOf(AbstractArrayStorage.STORAGE_LIMIT)));
    }
}