package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.ActionReportDao;
import com.finalproject.model.entity.ActionReport;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCActionReportFactory implements ActionReportDao {
    private Connection connection;
    private static ResourceBundle bundle = ResourceBundle.getBundle("database/queries");
    private final static Logger LOGGER = Logger.getLogger(JDBCActionReportFactory.class.getSimpleName());

    JDBCActionReportFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(ActionReport actionReport) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(bundle.getString("action.report.create"))) {
            preparedStatement.setString(1, actionReport.getAction().toString());
            preparedStatement.setString(2, actionReport.getMessage());
            preparedStatement.setObject(3, actionReport.getDate());
            preparedStatement.setInt(4, actionReport.getTaxReturnId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException while creating action report");
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
        actionReport.setDate(date.toLocalDateTime());
        actionReport.setTaxReturnId(rs.getInt("tax_return_id"));
        return actionReport;
    }

    @Override
    public ActionReport readId(int reportId) {
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("action.report.read.id"))) {
            ps.setInt(1, reportId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for action report by id: " + reportId);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ActionReport> readAll() {
        List<ActionReport> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("action.report.read.all"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for all action reports");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ActionReport> userList(int userId) {
        List<ActionReport> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("action.report.read.list.user.id"))) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for action report list by user id: " + userId);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(ActionReport actionReport, int id) {
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("action.report.update"))) {
            statement.setString(1, actionReport.getAction().toString());
            statement.setString(2, actionReport.getMessage());
            statement.setObject(3, actionReport.getDate());
            statement.setInt(4, actionReport.getTaxReturnId());
            statement.setInt(5, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException while updating action report by id: " + id);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int reportId) {
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("action.report.delete"))) {
            statement.setInt(1, reportId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException while deleting action report by id: " + reportId);
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

}
