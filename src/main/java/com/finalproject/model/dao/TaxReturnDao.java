package com.finalproject.model.dao;

import com.finalproject.model.entity.TaxReturn;

import java.util.List;
import java.util.Optional;

/**
 * TaxReturnDao is an interface that sets methods to implement in JDBCTaxReturnFactory.
 */
public interface TaxReturnDao extends GenericDao<TaxReturn> {

    /**
     * The method changes user's inspector.
     *
     * @param inspectorId inspector id.
     * @param userId      user id.
     * @return {@code true} changes successful or {@code false} if it's no.
     */
    boolean changeInspector(int inspectorId, int userId);

    /**
     * The method returns user's tax returns.
     *
     * @param userId user id.
     * @return {@code List<TaxReturn>} user's tax returns list.
     */
    List<TaxReturn> getUserTaxReturn(int userId);

    /**
     * The method returns all tax returns of users that belong to the inspector.
     *
     * @param inspectorId inspector id.
     * @return {@code List<TaxReturn>} tax returns list of all inspector's users.
     */
    List<TaxReturn> getInspectorTaxReturn(int inspectorId);

    /**
     * The method returns tax return by id.
     *
     * @param id tax return id.
     * @return {@code Optional<TaxReturn>} tax return.
     */
    Optional<TaxReturn> findById(int id);

    /**
     * The method returns tax return by action id.
     *
     * @param actionReportId action report id.
     * @return {@code TaxReturn} tax return.
     */
    TaxReturn getTaxReturnByActionId(int actionReportId);

    /**
     * The method checks for existence of tax returns action report.
     *
     * @param taxReturnId tax return id.
     * @return {@code true} if exist or {@code false} if it's not.
     */
    boolean taxReturnHasReport(int taxReturnId);

    /**
     * The method returns count of all tax returns of users that belong to the inspector.
     *
     * @param inspectorId inspector id.
     * @return count of all tax returns.
     */
    int getPageCount(int inspectorId);

    /**
     * The method returns tax return list in the range.
     *
     * @param offset      beginning of the range.
     * @param length      end of the range.
     * @param inspectorId inspector id.
     * @return {@code List<TaxReturn>} of tax return in the range.
     */
    List<TaxReturn> getInRange(int offset, int length, int inspectorId);
}
