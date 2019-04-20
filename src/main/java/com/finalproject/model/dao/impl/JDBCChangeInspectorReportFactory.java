package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.ChangeInspectorReportDao;
import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.entity.ChangeInspectorReport;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCChangeInspectorReportFactory implements ChangeInspectorReportDao {
    private Connection connection;
    private static ResourceBundle bundle = ResourceBundle.getBundle("database/queries");
    private final static Logger LOGGER = Logger.getLogger(JDBCChangeInspectorReportFactory.class.getSimpleName());


    JDBCChangeInspectorReportFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(ChangeInspectorReport entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(bundle.getString("change.inspector.create"));
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getPreviousInspectorId());
            preparedStatement.setInt(3, entity.getNewInspectorId());
            preparedStatement.setString(4, entity.getMessage());
            preparedStatement.setString(5, entity.getStatus().toString());
            preparedStatement.setObject(6, entity.getDate());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException while creating report");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ChangeInspectorReport extractFromResultSet(ResultSet rs) throws SQLException {
        ChangeInspectorReport changeInspectorReport = new ChangeInspectorReport();
        changeInspectorReport.setId(rs.getInt("id"));
        changeInspectorReport.setUserId(rs.getInt("user_id"));
        changeInspectorReport.setPreviousInspectorId(rs.getInt("previous_inspector_id"));
        changeInspectorReport.setNewInspectorId(rs.getInt("new_inspector_id"));
        changeInspectorReport.setMessage(rs.getString("message"));
        changeInspectorReport.setStatus(ChangeInspectorReport.Status.valueOf(rs.getString("status")));
        Timestamp date = (Timestamp) rs.getObject("date");
        changeInspectorReport.setDate(date.toLocalDateTime());
        return changeInspectorReport;
    }

    @Override
    public ChangeInspectorReport readId(int id) {
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("change.inspector.read.id"))) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching report with id: " + id);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ChangeInspectorReport> readAll() {
        List<ChangeInspectorReport> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("change.inspector.read.all"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for all reports");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(ChangeInspectorReport entity, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(bundle.getString("change.inspector.update"));
            preparedStatement.setInt(1, entity.getNewInspectorId());
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, entity.getStatus().toString());
            preparedStatement.setObject(4, entity.getDate());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            JDBCTaxReturnFactory taxReturnFactory = DaoFactory.getInstance().createTaxReturn();
            taxReturnFactory.changeInspector(entity.getNewInspectorId(), entity.getUserId());
            taxReturnFactory.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException while updating report with id: " + id);
            e.printStackTrace();
        }
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
    public List<ChangeInspectorReport> getByUserId(int userId) {
        List<ChangeInspectorReport> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("change.inspector.read.user.id"))) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for report by user id: " + userId);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ChangeInspectorReport> getInRange(int offset, int length, int userId) {
        List<ChangeInspectorReport> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("change.inspector.get.range"))) {
            statement.setInt(3, length);
            statement.setInt(1, userId);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for list of changeInspectorReport in range (" + offset +
                    ", " + length + ") by user id: " + userId);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkExistence(int userId) {
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("change.inspector.check.existence"))) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getPageCount(int userId) {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("change.inspector.page.count"))) {
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
