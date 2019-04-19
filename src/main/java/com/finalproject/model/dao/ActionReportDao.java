package com.finalproject.model.dao;

import com.finalproject.model.entity.ActionReport;

import java.util.List;

public interface ActionReportDao extends GenericDao<ActionReport> {

    List<ActionReport> userList(int userId);

}
