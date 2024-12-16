package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static void execute(ConnectionFactory factory, String statement, Executor executor) {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            executor.execute(ps);
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new ExistStorageException(e.getMessage());
            }
            throw new StorageException(e);
        }
    }

    public static <T> T resultExecute(ConnectionFactory factory, String statement, ResultExecutor<T> executor) {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Executor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    public interface ResultExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
