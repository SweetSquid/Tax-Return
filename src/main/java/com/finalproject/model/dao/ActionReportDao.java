package com.finalproject.model.dao;

import com.finalproject.model.entity.ActionReport;

import java.util.List;

/**
 * ActionReportDao is an interface that sets methods to implement in JDBCTActionReportFactory.
 */
public interface ActionReportDao extends GenericDao<ActionReport> {

    /**
     * The method returns user's action report list.
     *
     * @param userId user id.
     * @return {@code List<ActionReport>} action report list.
     */
    List<ActionReport> userList(int userId);

}
