package com.finalproject.model.dao;

import com.finalproject.model.entity.TaxReturn;

import java.util.List;
import java.util.Optional;

public interface TaxReturnDao extends GenericDao<TaxReturn> {
    boolean changeInspector(int inspectorId, int userId);

    List<TaxReturn> getUserTaxReturn(int userId);

    List<TaxReturn> getInspectorTaxReturn(int inspectorId);

    Optional<TaxReturn> findById(int id);

    TaxReturn getTaxReturnByActionId(int actionReportId);

    boolean taxReturnHasReport(int taxReturnId);

    int getPageCount(int inspectorId);

    List<TaxReturn> getInRange(int offset, int length, int inspectorId);
}
