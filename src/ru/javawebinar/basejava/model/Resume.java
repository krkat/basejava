package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.MapWithListAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.*;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;

    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    @XmlJavaTypeAdapter(MapWithListAdapter.class)
    private final Map<SectionType, List<Section>> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, List<Section>> getSections() {
        return sections;
    }

    public void addContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public String getSection(SectionType type) {
        List<Section> list = sections.get(type);
        if (list != null) {
            StringBuilder builder = new StringBuilder();
            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    for (Section section : list) {
                        builder.append(((TextSection) section).getContent());
                    }
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    for (Section section : list) {
                        List<String> items = ((ListSection) section).getItems();
                        for (String item : items) {
                            builder.append(item).append("\n");
                        }
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    builder.append("");
                }
                default -> builder.append("");
            }
            return builder.toString();
        }
        return "";
    }

    public void addSection(SectionType type, Section section) {
        if (!sections.containsKey(type)) {
            List<Section> list = new ArrayList<>();
            list.add(section);
            sections.put(type, list);
        } else {
            sections.get(type).add(section);
        }
    }

    public void deleteSection(SectionType sectionType, Section section) {
        if (sections.containsKey(sectionType)) {
            sections.get(sectionType).remove(section);
        }
    }

    public void print() {
        System.out.println(fullName);
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.print(entry.getKey().getTitle() + ": " + entry.getValue());
            System.out.println();
        }
        for (Map.Entry<SectionType, List<Section>> entry : sections.entrySet()) {
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