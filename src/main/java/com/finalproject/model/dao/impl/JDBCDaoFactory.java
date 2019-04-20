package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DAOFactory is a class that creates concrete DAO objects.
 */
public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    /**
     * The method creates connection.
     *
     * @return Connection object.
     */
    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method creates JDBCUserFactory with connection.
     *
     * @return created JDBCUserFactory object.
     */
    @Override
    public JDBCUserFactory createUser() {
        return new JDBCUserFactory(getConnection());
    }

    /**
     * The method creates JDBCTaxReturnFactory with connection.
     *
     * @return created JDBCTaxReturnFactory object.
     */
    @Override
    public JDBCTaxReturnFactory createTaxReturn() {
        return new JDBCTaxReturnFactory(getConnection());
    }

    /**
     * The method creates JDBCActionReportFactory with connection.
     *
     * @return created JDBCActionReportFactory object.
     */
    @Override
    public JDBCActionReportFactory createActionReport() {
        return new JDBCActionReportFactory(getConnection());
    }

    /**
     * The method creates JDBCHistoryFactory with connection.
     *
     * @return created JDBCHistoryFactory object.
     */
    @Override
    public JDBCHistoryFactory createHistory() {
        return new JDBCHistoryFactory(getConnection());
    }

    /**
     * The method creates JDBCChangeInspectorReportFactory with connection.
     *
     * @return created JDBCChangeInspectorReportFactory object.
     */
    @Override
    public JDBCChangeInspectorReportFactory createChangeInspectorReport() {
        return new JDBCChangeInspectorReportFactory(getConnection());
    }
}
