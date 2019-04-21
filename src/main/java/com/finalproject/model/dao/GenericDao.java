package com.finalproject.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * CRUD generic interface
 */
public interface GenericDao<T> extends AutoCloseable {

    boolean create(T entity) throws SQLException;

    T extractFromResultSet(ResultSet rs) throws SQLException;

    T readId(int id);

    List<T> readAll();

    boolean update(T t, int id) throws SQLException;

    boolean delete(int id);

    void close();

}
