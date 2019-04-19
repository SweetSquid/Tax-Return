package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.TaxReturnDao;
import com.finalproject.model.entity.TaxReturn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCTaxReturnFactory implements TaxReturnDao {
    private Connection connection;

    JDBCTaxReturnFactory(Connection connection) {
        this.connection = connection;
    }

    //TODO check CHANGE_INSPECTOR query
    @Override
    public boolean changeInspector(int inspectorId, int userId) {
        try (PreparedStatement ps = connection.prepareCall(TaxReturnSQL.CHANGE_INSPECTOR.QUERY)) {
            ps.setInt(1, inspectorId);
            ps.setInt(2, userId);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<TaxReturn> getUserTaxReturn(int userId) {
        List<TaxReturn> taxReturnList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(TaxReturnSQL.GET_ALL_USER_TAXRETURN.QUERY)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                taxReturnList.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxReturnList;
    }

    @Override
    public List<TaxReturn> getInspectorTaxReturn(int inspectorId) {
        List<TaxReturn> taxReturnList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(TaxReturnSQL.GET_ALL_INSPECTOR_TAXRETURN.QUERY)) {
            ps.setInt(1, inspectorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaxReturn taxReturn = extractFromResultSet(rs);
                if (!taxReturnHasReport(taxReturn.getId())) {
                    taxReturnList.add(taxReturn);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxReturnList;
    }


    @Override
    public Optional<TaxReturn> findById(int taxReturnId) {
        Optional<TaxReturn> taxReturn = Optional.empty();

        String query = "SELECT * FROM tax_return WHERE tax_return_id = ?";
        try (PreparedStatement ps = connection.prepareCall(query)) {
            ps.setInt(1, taxReturnId);
            ResultSet rs = ps.executeQuery();
            //если надо выводить пару значений, поменять if на while
            if (rs.next()) {
                taxReturn = Optional.of(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxReturn;
    }

    @Override
    public TaxReturn getTaxReturnByActionId(int actionReportId) {
        TaxReturn taxReturn = new TaxReturn();
        try (PreparedStatement ps = connection.prepareCall(TaxReturnSQL.GET_TAX_BY_ACTION_ID.QUERY)) {
            ps.setInt(1, actionReportId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                taxReturn = extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxReturn;
    }

    @Override
    public boolean taxReturnHasReport(int taxReturnId) {
        try (PreparedStatement ps = connection.prepareCall(TaxReturnSQL.TAX_HAS_REPORT.QUERY)) {
            ps.setInt(1, taxReturnId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean create(TaxReturn taxReturn) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(TaxReturnSQL.CREATE.QUERY);
            preparedStatement.setInt(1, taxReturn.getUserId());
            preparedStatement.setInt(2, taxReturn.getInspectorId());
            preparedStatement.setString(3, taxReturn.getCategory());
            preparedStatement.setObject(4, taxReturn.getDate());
            preparedStatement.setDouble(5, taxReturn.getWage());
            preparedStatement.setDouble(6, taxReturn.getMilitaryCollection());
            preparedStatement.setDouble(7, taxReturn.getIncomeTax());
            result = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TaxReturn extractFromResultSet(ResultSet rs) throws SQLException {
        TaxReturn taxReturn = new TaxReturn();
        taxReturn.setId(rs.getInt("tax_return_id"));
        taxReturn.setUserId(rs.getInt("user_id"));
        taxReturn.setInspectorId(rs.getInt("inspector_id"));
        taxReturn.setCategory(rs.getString("category_id"));
        taxReturn.setWage(rs.getDouble("wage"));
        taxReturn.setMilitaryCollection(rs.getDouble("military_collection"));
        taxReturn.setIncomeTax(rs.getDouble("income_tax"));
        Timestamp date = (Timestamp) rs.getObject("date");
        //TODO убрать t из вывода
        taxReturn.setDate(date.toLocalDateTime());
        return taxReturn;

    }

    @Override
    public TaxReturn readId(int id) {
        return null;
    }

    @Override
    public List<TaxReturn> readAll() {

        return null;
    }

    @Override
    public boolean update(TaxReturn taxReturn, int taxReturnId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(TaxReturnSQL.UPDATE.QUERY);
            preparedStatement.setString(1, taxReturn.getCategory());
            preparedStatement.setObject(2, taxReturn.getDate());
            preparedStatement.setDouble(3, taxReturn.getWage());
            preparedStatement.setDouble(4, taxReturn.getMilitaryCollection());
            preparedStatement.setDouble(5, taxReturn.getIncomeTax());
            preparedStatement.setInt(6, taxReturnId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
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

    public Integer getInspectorId(int userId) {
        try (PreparedStatement ps = connection.prepareCall(TaxReturnSQL.GET_INSPECTOR_ID.QUERY)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractFromResultSet(rs).getInspectorId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaxReturn> getInRange(int offset, int length, int inspectorId) {
        List<TaxReturn> result = new ArrayList<>();

        String query = "SELECT a.* FROM tax_return a LEFT JOIN action_report b ON a.tax_return_id = b.tax_return_id WHERE b.tax_return_id IS NULL AND inspector_id = ? LIMIT ?, ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(3, length);
            statement.setInt(2, offset);
            statement.setInt(1, inspectorId);
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
    public int getPageCount(int inpectorId) {
        int result = 0;
        String query = "SELECT COUNT(*)" +
                "FROM tax_return a" +
                "       LEFT JOIN action_report b ON b.tax_return_id = a.tax_return_id " +
                "WHERE b.tax_return_id IS NULL AND inspector_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, inpectorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    enum TaxReturnSQL {
        CREATE("INSERT INTO tax_return (tax_return_id, user_id, inspector_id, category_id, date, wage, military_collection, income_tax) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)"),
        UPDATE("UPDATE tax_return SET category_id = ?, date = ?, wage = ?, military_collection = ?, income_tax = ? WHERE tax_return_id= ?"),
        CHANGE_INSPECTOR("UPDATE tax_return SET inspector_id = ? WHERE user_id = ?"),
        GET_ALL_USER_TAXRETURN("SELECT * FROM tax_return WHERE user_id = ?"),
        GET_TAX_BY_ACTION_ID("SELECT a.* FROM tax_return a LEFT JOIN action_report b ON a.tax_return_id = b.tax_return_id WHERE b.report_id = ? AND action = 'EDIT'"),
        TAX_HAS_REPORT("SELECT * FROM action_report WHERE tax_return_id = ?"),
        GET_INSPECTOR_ID("SELECT * FROM tax_return WHERE user_id = ?"),
        GET_ALL_INSPECTOR_TAXRETURN("SELECT *\n" +
                "FROM tax_return a\n" +
                "       LEFT JOIN action_report b ON b.tax_return_id = a.tax_return_id\n" +
                "WHERE b.tax_return_id IS NULL AND inspector_id = ?");

        String QUERY;

        TaxReturnSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
