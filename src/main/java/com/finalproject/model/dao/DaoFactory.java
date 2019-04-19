package com.finalproject.model.dao;


import com.finalproject.model.dao.impl.*;

public abstract class DaoFactory {

    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {

        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }

        return daoFactory;
    }

    public abstract JDBCUserFactory createUser();

    public abstract JDBCTaxReturnFactory createTaxReturn();

    public abstract JDBCActionReportFactory createActionReport();

    public abstract JDBCHistoryFactory createHistory();

    public abstract JDBCChangeInspectorReportFactory createChangeInspectorReport();
}
