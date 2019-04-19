package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.HistoryDao;
import com.finalproject.model.entity.ActionReport.Action;
import com.finalproject.model.entity.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCHistoryFactory implements HistoryDao {
    private Connection connection;

    JDBCHistoryFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(History history) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(JDBCHistorySQL.CREATE.QUERY);
            preparedStatement.setInt(1, history.getTaxReturnId());
            preparedStatement.setInt(2, history.getUserId());
            preparedStatement.setString(3, history.getAction().toString());
            preparedStatement.setString(4, history.getMessage());
            preparedStatement.setObject(5, history.getDate());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
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
        //TODO убрать t из вывода
        history.setDate(date.toLocalDateTime());
        return history;
    }

    @Override
    public History readId(int id) {
        History result = new History();
        try (PreparedStatement ps = connection.prepareCall(JDBCHistorySQL.READ.QUERY)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<History> readAll() {
        List<History> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(JDBCHistorySQL.READ_ALL.QUERY)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
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
        try (PreparedStatement ps = connection.prepareCall(JDBCHistorySQL.READ_BY_USER.QUERY)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<History> getInRange(int offset, int length, int userId) {
        List<History> result = new ArrayList<>();

        String query = "SELECT * FROM history WHERE user_id = ? LIMIT ?, ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPageCount(int userId) {
        int result = 0;
        String query = "SELECT COUNT(*) FROM history WHERE user_id = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    enum JDBCHistorySQL {
        READ("SELECT a.tax_return_id, a.report_id, b.user_id, a.action, a.message, a.date\n" +
                "FROM action_report a\n" +
                "       LEFT JOIN tax_return b ON a.tax_return_id = b.tax_return_id" +
                "WHERE history_id = ?;"),
        READ_BY_USER("SELECT * FROM history WHERE user_id = ?"),
        CREATE("INSERT INTO history (tax_return_id, user_id, action, message, date) VALUES (?, ?, ?, ?, ?)"),
        READ_ALL("SELECT * FROM history");

        String QUERY;

        JDBCHistorySQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
