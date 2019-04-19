package com.finalproject.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    private static ResourceBundle bundle = ResourceBundle.getBundle("database/connection");

    static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName(bundle.getString("mysql.driver"));
                    ds.setUrl(bundle.getString("mysql.url"));
                    ds.setUsername(bundle.getString("mysql.user"));
                    ds.setPassword(bundle.getString("mysql.password"));
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }

}