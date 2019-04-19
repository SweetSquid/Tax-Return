package com.finalproject.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    boolean create(T entity);

    T extractFromResultSet(ResultSet rs) throws SQLException;

    T readId(int id);

    List<T> readAll();

    boolean update(T t, int id);

    boolean delete(int id);

    void close();

}
