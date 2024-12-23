package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e.getMessage());
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name=? WHERE r.uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r);
            insertContacts(conn, r);
            deleteSections(r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     WHERE r.uuid=? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(rs, r);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("    SELECT * FROM resume r " +
                    " LEFT JOIN section s " +
                    "        ON r.uuid = s.resume_uuid " +
                    "     WHERE r.uuid=? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                do {
                    addSection(rs, r);
                } while (rs.next());
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    "  ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    resumes.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM contact c ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");
                    if (resumes.containsKey(resumeUuid)) {
                        addContact(rs, resumes.get(resumeUuid));
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM section s ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");
                    if (resumes.containsKey(resumeUuid)) {
                        addSection(rs, resumes.get(resumeUuid));
                    }
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void deleteContacts(Resume r) {
        executeDelete(r, "DELETE  FROM contact WHERE resume_uuid=?");
    }

    private void executeDelete(Resume r, String query) {
        sqlHelper.execute(query, ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Resume r) {
        executeDelete(r,"DELETE  FROM section WHERE resume_uuid=?");
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            String uuid = r.getUuid();
            for (Map.Entry<SectionType, List<Section>> entry : r.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                List<Section> sections = entry.getValue();
                if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
                    for (Section section : sections) {
                        ps.setString(1, uuid);
                        ps.setString(2, sectionType.name());
                        ps.setString(3, section.toString());
                        ps.addBatch();
                    }
                }
                if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    for (Section section : sections) {
                        List<String> items = ((ListSection) section).getItems();
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < items.size(); i++) {
                            builder.append(items.get(i));
                            if (i < (items.size() - 1)) {
                                builder.append("\n");
                            }
                        }
                        ps.setString(1, uuid);
                        ps.setString(2, sectionType.name());
                        ps.setString(3, builder.toString());
                        ps.addBatch();
                    }
                }
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (value != null && type != null) {
            r.addContact(ContactType.valueOf(type), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (value != null && type != null) {
            if (type.equals(SectionType.PERSONAL.name()) || type.equals(SectionType.OBJECTIVE.name())) {
                r.addSection(SectionType.valueOf(type), new TextSection(value));
            }
            if (type.equals(SectionType.ACHIEVEMENT.name()) || type.equals(SectionType.QUALIFICATIONS.name())) {
                r.addSection(SectionType.valueOf(type), new ListSection(new ArrayList<>(Arrays.asList(value.split("\n")))));
            }
        }
    }
}