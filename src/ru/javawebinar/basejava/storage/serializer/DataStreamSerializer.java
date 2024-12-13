package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readContacts(resume, dis);
            readSections(resume, dis);
            return resume;
        }
    }

    private static void writeContacts(Resume r, DataOutputStream dos) throws IOException {
        Map<ContactType, String> contacts = r.getContacts();
        writeCollection(contacts.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private static void writeSections(Resume r, DataOutputStream dos) throws IOException {
        Map<SectionType, List<Section>> map = r.getSections();
        writeCollection(map.entrySet(), dos, entry -> {
            SectionType type = entry.getKey();
            dos.writeUTF(type.name());
            List<Section> sections = entry.getValue();
            writeCollection(sections, dos, section -> {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> writeTextSection(section, dos);
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(section, dos);
                    case EXPERIENCE, EDUCATION -> writeCompanySection(section, dos);
                }
            });
        });
    }

    private static void writeTextSection(Section section, DataOutputStream dos) throws IOException {
        dos.writeUTF(((TextSection) section).getContent());
    }

    private static void writeListSection(Section section, DataOutputStream dos) throws IOException {
        List<String> items = ((ListSection) section).getItems();
        writeCollection(items, dos, dos::writeUTF);
    }

    private static void writeCompanySection(Section section, DataOutputStream dos) throws IOException {
        writeLink(((CompanySection) section).getName(), ((CompanySection) section).getWebsite(), dos);

        List<Period> periods = ((CompanySection) section).getPeriods();
        writeCollection(periods, dos, (period) -> {
            dos.writeUTF(period.getStartDate().toString());
            dos.writeUTF(period.getEndDate().toString());
            dos.writeUTF(period.getPosition());
            dos.writeUTF(period.getDescription());
        });
    }

    private static void writeLink(String name, String website, DataOutputStream dos) throws IOException {
        dos.writeUTF(name);
        if (website != null) {
            dos.writeBoolean(true);
            dos.writeUTF(website);
        } else {
            dos.writeBoolean(false);
        }
    }

    private static void readContacts(Resume resume, DataInputStream dis) throws IOException {
        processItems(dis, () -> {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        });
    }

    private static void readSections(Resume resume, DataInputStream dis) throws IOException {
        processItems(dis, () -> {
            SectionType type = SectionType.valueOf(dis.readUTF());
            readList(dis, () -> {
                Section section = switch (type) {
                    case PERSONAL, OBJECTIVE -> readTextSection(dis);
                    case ACHIEVEMENT, QUALIFICATIONS -> readListSection(dis);
                    case EXPERIENCE, EDUCATION -> readCompanySection(dis);
                };
                resume.addSection(type, section);
                return section;
            });
        });
    }

    private static Section readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private static Section readListSection(DataInputStream dis) throws IOException {
        return new ListSection(readList(dis, () -> dis.readUTF()));
    }

    private static Section readCompanySection(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String website = readString(dis);

        List<Period> periods = new ArrayList<>(readList(dis, () -> {
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            String position = dis.readUTF();
            String description = dis.readUTF();
            return new Period(startDate, endDate, position, description);
        }));
        return new CompanySection(name, website, periods);
    }

    private static String readString(DataInputStream dis) throws IOException {
        return dis.readBoolean() ? dis.readUTF() : null;
    }

    private static <T> void writeCollection(Collection<T> collection, DataOutputStream dos, ElementWriter<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T obj : collection) {
            consumer.write(obj);
        }
    }

    private static <T> List<T> readList(DataInputStream dis, ElementReader<T> producer) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(producer.read());
        }
        return list;
    }

    private static void processItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    public interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    public interface ElementWriter<T> {
        void write(T t) throws IOException;
    }
}