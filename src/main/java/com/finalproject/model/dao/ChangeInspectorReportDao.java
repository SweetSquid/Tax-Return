package com.finalproject.model.dao;

import com.finalproject.model.entity.ChangeInspectorReport;

import java.util.List;

public interface ChangeInspectorReportDao extends GenericDao<ChangeInspectorReport> {
    List<ChangeInspectorReport> getByUserId(int userId);

    int getPageCount(int userId);

    List<ChangeInspectorReport> getInRange(int offset, int length, int inspectorId);
}
