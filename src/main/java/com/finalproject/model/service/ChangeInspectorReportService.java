package com.finalproject.model.service;

import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCChangeInspectorReportFactory;
import com.finalproject.model.entity.ChangeInspectorReport;

import java.time.LocalDateTime;
import java.util.List;

public class ChangeInspectorReportService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private int postOnPage = 6;

    public List<ChangeInspectorReport> readAll() {
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            return dao.readAll();
        }
    }

    public ChangeInspectorReport id(int id) {
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            return dao.readId(id);
        }
    }

    public boolean update(ChangeInspectorReport entity, int id) {
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            return dao.update(entity, id);
        }
    }

    public boolean create(int userId, int prevInspId, String message, ChangeInspectorReport.Status status) {
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            ChangeInspectorReport changeInspectorReport = new ChangeInspectorReport();
            changeInspectorReport.setUserId(userId);
            changeInspectorReport.setPreviousInspectorId(prevInspId);
            changeInspectorReport.setMessage(message);
            changeInspectorReport.setStatus(status);
            changeInspectorReport.setDate(LocalDateTime.now());
            return dao.create(changeInspectorReport);
        }
    }

    public List<ChangeInspectorReport> getByUserId(int userId) {
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            return dao.getByUserId(userId);
        }
    }

    public List<ChangeInspectorReport> getExpoList(String currentPage, int userId) {
        int currentPageInt = currentPage != null ? Integer.parseInt(currentPage) : 1;
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            currentPageInt = currentPageInt <= 0 ? 0 : (currentPageInt - 1) * postOnPage;
            return dao.getInRange(currentPageInt, postOnPage, userId);
        }
    }

    public int getPageCount(int userId) {
        try (JDBCChangeInspectorReportFactory dao = daoFactory.createChangeInspectorReport()) {
            System.out.println(dao.getPageCount(userId));
            return dao.getPageCount(userId) % postOnPage == 0 ? dao.getPageCount(userId) / postOnPage :
                    dao.getPageCount(userId) / postOnPage + 1;
        }
    }
}


