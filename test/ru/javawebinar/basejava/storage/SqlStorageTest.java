package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.ResumeInfo;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

    @Test
    public void updateAddContact() {
        Resume resumeWithoutContacts = new Resume(UUID.randomUUID().toString(), "Name");
        storage.save(resumeWithoutContacts);
        Resume updatedResume = new Resume(resumeWithoutContacts.getUuid(), resumeWithoutContacts.getFullName());
        updatedResume.addContact(ContactType.PHONE, "+71234567");
        ResumeInfo.addTextSections(updatedResume);
        ResumeInfo.addListSections(updatedResume);
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get(resumeWithoutContacts.getUuid()));
    }

    @Test
    public void updateDeleteContact() {
        Resume newResume = new Resume(UUID_1, RESUME_1.getFullName());
        ResumeInfo.addTextSections(newResume);
        ResumeInfo.addListSections(newResume);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    public void updateAddSection() {
        Resume resumeWithoutContacts = new Resume(UUID.randomUUID().toString(), "Name");
        storage.save(resumeWithoutContacts);
        Resume updatedResume = new Resume(resumeWithoutContacts.getUuid(), resumeWithoutContacts.getFullName());
        updatedResume.addContact(ContactType.PHONE, "+71234567");
        ResumeInfo.addTextSections(updatedResume);
        ResumeInfo.addListSections(updatedResume);
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get(resumeWithoutContacts.getUuid()));
    }

    @Test
    public void updateDeleteTextSection() {
        Resume newResume = new Resume(UUID_1, RESUME_1.getFullName());
        ResumeInfo.addTextSections(newResume);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }
}
