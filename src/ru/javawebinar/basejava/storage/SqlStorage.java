package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    private static final String SELECT_ALL = "SELECT * FROM resume";
    private static final String DELETE_ALL = "DELETE FROM resume";
    public static final String UPDATE = "UPDATE resume r SET full_name=? WHERE r.uuid=?";
    public static final String INSERT = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
    public static final String DELETE = "DELETE FROM resume r WHERE r.uuid=?";
    public static final String GET = "SELECT * FROM resume r WHERE r.uuid=?";

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.execute(connectionFactory, DELETE_ALL, PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        SqlHelper.execute(connectionFactory, UPDATE, ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.executeUpdate();
        });
    }

    @Override
    public void save(Resume r) {
        SqlHelper.execute(connectionFactory, INSERT, ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.resultExecute(connectionFactory, GET, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.execute(connectionFactory, DELETE, ps -> {
            ps.setString(1, uuid);
            if (!ps.execute()) {
                throw new NotExistStorageException("Not exist");
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.resultExecute(connectionFactory, SELECT_ALL, ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            Collections.sort(resumes);
            return resumes;
        });
    }

    @Override
    public int size() {
        return SqlHelper.resultExecute(connectionFactory, SELECT_ALL, ps -> {
            ResultSet rs = ps.executeQuery();
            int size = 0;
            while (rs.next()) {
                rs.getString("uuid");
                rs.getString("full_name");
                size++;
            }
            return size;
        });
    }
}