package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
    /*public static void execute(ConnectionFactory factory, String statement, Executor executor) {
        try (Connection conn = factory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            executor.execute(ps);
        } catch (SQLException e) {
            if ("23505". equals(e.getSQLState())) {
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
    }*/
}