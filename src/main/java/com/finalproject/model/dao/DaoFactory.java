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

    /**
     * @return instance of JDBCUserFactory.
     */
    public abstract JDBCUserFactory createUser();

    /**
     * @return instance of JDBCTaxReturnFactory.
     */
    public abstract JDBCTaxReturnFactory createTaxReturn();

    /**
     * @return instance of JDBCActionReportFactory.
     */
    public abstract JDBCActionReportFactory createActionReport();

    /**
     * @return instance of JDBCHistoryFactory.
     */
    public abstract JDBCHistoryFactory createHistory();

    /**
     * @return instance of JDBCChangeInspectorReportFactory.
     */
    public abstract JDBCChangeInspectorReportFactory createChangeInspectorReport();
}
