package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeContacts(r, dos);
            writeSections(r, dos);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(resume, dis);
            readSections(resume, dis);
            return resume;
        }
    }

    private static void writeContacts(Resume r, DataOutputStream dos) throws IOException {
        Map<ContactType, String> contacts = r.getContacts();
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private static void writeSections(Resume r, DataOutputStream dos) throws IOException {
        Map<SectionType, List<Section>> map = r.getSections();
        dos.writeInt(map.size());
        for (Map.Entry<SectionType, List<Section>> entry : map.entrySet()) {
            SectionType type = entry.getKey();
            dos.writeUTF(type.name());
            List<Section> sections = entry.getValue();
            dos.writeInt(sections.size());
            for (Section section : sections) {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> writeTextSection(section, dos);
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(section, dos);
                    case EXPERIENCE, EDUCATION -> writeCompanySection(section, dos);
                }
            }
        }
    }

    private static void writeTextSection(Section section, DataOutputStream dos) throws IOException {
        dos.writeUTF(((TextSection) section).getContent());
    }

    private static void writeListSection(Section section, DataOutputStream dos) throws IOException {
        List<String> items = ((ListSection) section).getItems();
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private static void writeCompanySection(Section section, DataOutputStream dos) throws IOException {
        dos.writeUTF(((CompanySection) section).getName());
        dos.writeUTF(((CompanySection) section).getWebsite());

        List<Period> periods = ((CompanySection) section).getPeriods();
        dos.writeInt(periods.size());
        for (Period period : periods) {
            dos.writeUTF(period.getStartDate().toString());
            dos.writeUTF(period.getEndDate().toString());
            dos.writeUTF(period.getPosition());
            dos.writeUTF(period.getDescription());
        }
    }

    private static void readContacts(Resume resume, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private static void readSections(Resume resume, DataInputStream dis) throws IOException {
        int sectionsMapSize = dis.readInt();
        for (int i = 0; i < sectionsMapSize; i++) {
            SectionType type = SectionType.valueOf(dis.readUTF());
            int sectionsLiatSize = dis.readInt();
            for (int j = 0; j < sectionsLiatSize; j++) {
                Section section = switch (type) {
                    case PERSONAL, OBJECTIVE -> readTextSection(dis);
                    case ACHIEVEMENT, QUALIFICATIONS -> readListSection(dis);
                    case EXPERIENCE, EDUCATION -> readCompanySection(dis);
                };
                resume.addSection(type, section);
            }
        }
    }

    private static Section readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private static Section readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
        return new ListSection(items);
    }

    private static Section readCompanySection(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String website = dis.readUTF();
        int size = dis.readInt();
        List<Period> periods = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            String position = dis.readUTF();
            String description = dis.readUTF();
            periods.add(new Period(startDate, endDate, position, description));
        }
        return new CompanySection(name, website, periods);
    }
}