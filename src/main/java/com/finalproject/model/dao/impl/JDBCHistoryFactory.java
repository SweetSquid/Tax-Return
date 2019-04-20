package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.HistoryDao;
import com.finalproject.model.entity.ActionReport.Action;
import com.finalproject.model.entity.History;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCHistoryFactory implements HistoryDao {
    private Connection connection;
    private static ResourceBundle bundle = ResourceBundle.getBundle("database/queries");
    private final static Logger LOGGER = Logger.getLogger(JDBCHistoryFactory.class.getSimpleName());

    JDBCHistoryFactory(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(History history) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(bundle.getString("history.create"));
            preparedStatement.setInt(1, history.getTaxReturnId());
            preparedStatement.setInt(2, history.getUserId());
            preparedStatement.setString(3, history.getAction().toString());
            preparedStatement.setString(4, history.getMessage());
            preparedStatement.setObject(5, history.getDate());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException while creating history ");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public History extractFromResultSet(ResultSet rs) throws SQLException {
        History history = new History();
        history.setTaxReturnId(rs.getInt("tax_return_id"));
        history.setUserId(rs.getInt("user_id"));
        history.setAction(Action.valueOf(rs.getString("action")));
        history.setMessage(rs.getString("message"));
        Timestamp date = (Timestamp) rs.getObject("date");
        history.setDate(date.toLocalDateTime());
        return history;
    }

    @Override
    public History readId(int id) {
        History result = new History();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("history.read.id"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for history with id: " + id);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<History> readAll() {
        List<History> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("history.read.all"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for all history");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(History history, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<History> getByUser(int userId) {
        List<History> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("history.read.by.user"))) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for history list by user id: " + userId);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<History> getInRange(int offset, int length, int userId) {
        List<History> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("history.get.range"))) {
            statement.setInt(1, userId);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for list of history in range (" + offset +
                    ", " + length + ") by user id: " + userId);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPageCount(int userId) {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("history.get.page.count"))) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for count of query");
            throw new RuntimeException(e);
        }
    }

}
