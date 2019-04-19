package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.DaoFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JDBCUserFactory createUser() {
        return new JDBCUserFactory(getConnection());
    }

    @Override
    public JDBCTaxReturnFactory createTaxReturn() {
        return new JDBCTaxReturnFactory(getConnection());
    }

    @Override
    public JDBCActionReportFactory createActionReport() {
        return new JDBCActionReportFactory(getConnection());
    }

    @Override
    public JDBCHistoryFactory createHistory() {
        return new JDBCHistoryFactory(getConnection());
    }

    @Override
    public JDBCChangeInspectorReportFactory createChangeInspectorReport() {
        return new JDBCChangeInspectorReportFactory(getConnection());
    }
}
