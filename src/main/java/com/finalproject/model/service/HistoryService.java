package com.finalproject.model.service;

import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCHistoryFactory;
import com.finalproject.model.entity.ActionReport;
import com.finalproject.model.entity.History;

import java.time.LocalDateTime;
import java.util.List;

public class HistoryService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final int POST_ON_PAGE = 7;

    public boolean create(int taxId, int userId, ActionReport.Action action, String message) {
        try (JDBCHistoryFactory historyDao = daoFactory.createHistory()) {
            History history = new History();
            history.setTaxReturnId(taxId);
            history.setUserId(userId);
            history.setAction(action);
            history.setDate(LocalDateTime.now());
            history.setMessage(message);
            historyDao.create(history);
        }
        return false;
    }

    public boolean delete(int historyId) {
        try (JDBCHistoryFactory historyDao = daoFactory.createHistory()) {
            historyDao.delete(historyId);
            return true;
        }
    }


    public List<History> getHistoryList(String currentPage, int userId) {
        int currentPageInt = currentPage != null ? Integer.parseInt(currentPage) : 1;
        try (JDBCHistoryFactory historyDao = daoFactory.createHistory()) {
            currentPageInt = currentPageInt <= 0 ? 0 : (currentPageInt - 1) * POST_ON_PAGE;
            return historyDao.getInRange(currentPageInt, POST_ON_PAGE, userId);
        }
    }

    public int getPageCount(int userId) {
        try (JDBCHistoryFactory historyDao = daoFactory.createHistory()) {
            return historyDao.getPageCount(userId) / POST_ON_PAGE + 1;
        }
    }
}
