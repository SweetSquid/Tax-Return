package com.finalproject.model.dao;

import com.finalproject.model.entity.ChangeInspectorReport;

import java.util.List;

/**
 * ChangeInspectorReportDao is an interface that sets methods to implement in JDBCTChangeInspectorReportFactory.
 */
public interface ChangeInspectorReportDao extends GenericDao<ChangeInspectorReport> {

    /**
     * The method returns list of user reports.
     *
     * @param userId user id.
     * @return {@code List<ChangeInspectorReport>} reports list.
     */
    List<ChangeInspectorReport> getByUserId(int userId);

    /**
     * The method returns count of all user's reports.
     *
     * @param userId user id.
     * @return count of all tax returns.
     */
    int getPageCount(int userId);

    /**
     * The method returns reports list in the range.
     *
     * @param offset      beginning of the range.
     * @param length      end of the range.
     * @param inspectorId inspector id.
     * @return {@code List<ChangeInspectorReport>} of tax return in the range.
     */
    List<ChangeInspectorReport> getInRange(int offset, int length, int inspectorId);

    /**
     * The method checks for existence of user's report.
     *
     * @param userId user id.
     * @return {@code true} if exist or {@code false} if it's not.
     */
    boolean checkExistence(int userId);
}
