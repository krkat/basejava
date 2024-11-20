package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, List<Contact>> contacts = new LinkedHashMap<>();

    private final Map<SectionType, List<Section>> sections = new LinkedHashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "FullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, List<Contact>> getContacts() {
        return Collections.unmodifiableMap(contacts);
    }

    public Map<SectionType, List<Section>> getSections() {
        return Collections.unmodifiableMap(sections);
    }

    public void addContact(ContactType contactType, Contact contact) {
        if (!contacts.containsKey(contactType)) {
            List<Contact> list = new ArrayList<>();
            list.add(contact);
            contacts.put(contactType, list);
        } else {
            contacts.get(contactType).add(contact);
        }
    }

    public void deleteContact(ContactType contactType, Contact contact) {
        if (contacts.containsKey(contactType)) {
            contacts.get(contactType).remove(contact);
        }
    }

    public void addSection(SectionType sectionType, Section section) {
        if (!sections.containsKey(sectionType)) {
            List<Section> list = new ArrayList<>();
            list.add(section);
            sections.put(sectionType, list);
        } else {
            sections.get(sectionType).add(section);
        }
    }

    public void deleteSection(SectionType sectionType, Section section) {
        if (sections.containsKey(sectionType)) {
            sections.get(sectionType).remove(section);
        }
    }

    public void print() {
        System.out.println(fullName);
        for(Map.Entry<ContactType, List<Contact>> entry : contacts.entrySet()) {
            System.out.print(entry.getKey().getTitle() + ": ");
            entry.getValue().forEach((c) -> System.out.print(c + " "));
            System.out.println();
        }
        System.out.println();
        for(Map.Entry<SectionType, List<Section>> entry : sections.entrySet()) {
            System.out.println(entry.getKey().getTitle() + "\n");
            entry.getValue().forEach(System.out::println);
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object r) {
        if (this == r) return true;
        if (r == null || getClass() != r.getClass()) return false;
        Resume resume = (Resume) r;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')' +
        " contacts=" + contacts +
                ", sections=" + sections;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}