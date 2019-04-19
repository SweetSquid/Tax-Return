package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.ActionReportDao;
import com.finalproject.model.entity.ActionReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCActionReportFactory implements ActionReportDao {
    private Connection connection;

    JDBCActionReportFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(ActionReport actionReport) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ActionReportSQL.CREATE_ACTION_REPORT.QUERY);
            preparedStatement.setString(1, actionReport.getAction().toString());
            preparedStatement.setString(2, actionReport.getMessage());
            preparedStatement.setObject(3, actionReport.getDate());
            preparedStatement.setInt(4, actionReport.getTaxReturnId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ActionReport extractFromResultSet(ResultSet rs) throws SQLException {
        ActionReport actionReport = new ActionReport();
        actionReport.setReport_id(rs.getInt("report_id"));
        actionReport.setAction(ActionReport.Action.valueOf(rs.getString("action")));
        actionReport.setMessage(rs.getString("message"));
        Timestamp date = (Timestamp) rs.getObject("date");
        //TODO убрать t из вывода
        actionReport.setDate(date.toLocalDateTime());
        ;
        actionReport.setTaxReturnId(rs.getInt("tax_return_id"));
        return actionReport;
    }

    @Override
    public ActionReport readId(int reportId) {
        try (PreparedStatement ps = connection.prepareCall(ActionReportSQL.READ_ID.QUERY)) {
            ps.setInt(1, reportId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ActionReport> readAll() {
        List<ActionReport> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(ActionReportSQL.READ_ALL.QUERY)) {
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
    public List<ActionReport> userList(int userId) {
        List<ActionReport> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(ActionReportSQL.READ_ACTION_REPORT_LIST_BY_USER.QUERY)) {
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
    public boolean update(ActionReport actionReport, int id) {
        //TODO create update
        return false;
    }

    @Override
    public boolean delete(int reportId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ActionReportSQL.DELETE_REPORT.QUERY);
            statement.setInt(1, reportId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

        }
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

    enum ActionReportSQL {
        CREATE_ACTION_REPORT("INSERT INTO action_report (report_id, action, message, date, tax_return_id) VALUES (DEFAULT, ?, ?, ?, ?)"),
        READ_ACTION_REPORT_LIST_BY_USER("SELECT a.* FROM action_report a LEFT JOIN tax_return b ON a.tax_return_id = b.tax_return_id WHERE b.user_id = ?"),
        READ_ALL("SELECT * FROM action_report"),
        READ_ID("SELECT * FROM action_report WHERE report_id = ?"),
        DELETE_REPORT("DELETE FROM action_report WHERE report_id = ?");
        String QUERY;

        ActionReportSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
