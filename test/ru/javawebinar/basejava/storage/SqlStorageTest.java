package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.assertEquals;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

    @Test
    public void updateAddContact() {
        Resume newResume = new Resume(UUID_1, "New Name");
        newResume.addContact(ContactType.PHONE, "+71234567");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    public void updateDeleteContact() {
        Resume newResume = new Resume(UUID_2, "New Name");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_2));
    }
}
