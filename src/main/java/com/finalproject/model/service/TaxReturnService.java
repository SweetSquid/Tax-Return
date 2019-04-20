package com.finalproject.model.service;

import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.TaxReturnDao;
import com.finalproject.model.dao.impl.JDBCTaxReturnFactory;
import com.finalproject.model.entity.TaxReturn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaxReturnService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private int postOnPage = 3;

    public Optional<TaxReturn> id(int id) {
        Optional<TaxReturn> result;
        try (TaxReturnDao taxReturnDao = daoFactory.createTaxReturn()) {
            result = taxReturnDao.findById(id);
        }
        return result;
    }

    public TaxReturn create(int userId, int inspectorId, String category, Double wage, Double militaryCollection, Double incomeTax) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            TaxReturn taxReturn = new TaxReturn();
            taxReturn.setUserId(userId);
            taxReturn.setInspectorId(inspectorId);
            taxReturn.setCategory(category);
            taxReturn.setDate(LocalDateTime.now());
            taxReturn.setWage(wage);
            taxReturn.setMilitaryCollection(militaryCollection);
            taxReturn.setIncomeTax(incomeTax);
            dao.create(taxReturn);
            return taxReturn;
        }
    }

    public TaxReturn getTaxReturnByActionId(int id) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            return dao.getTaxReturnByActionId(id);
        }
    }

    public int getInspectorId(int userId) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            return dao.getInspectorId(userId);
        }
    }

    public TaxReturn edit(int editActionId, String category,
                          String wage, String militaryCollection, String incomeTax) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            TaxReturn taxReturn = dao.getTaxReturnByActionId(editActionId);
            taxReturn.setCategory(category);
            taxReturn.setDate(LocalDateTime.now());
            taxReturn.setWage(Double.parseDouble(wage));
            taxReturn.setMilitaryCollection(Double.parseDouble(militaryCollection));
            taxReturn.setIncomeTax(Double.parseDouble(incomeTax));
            dao.update(taxReturn, taxReturn.getId());
            return taxReturn;
        }
    }

    public boolean taxReturnHasReport(int id) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            return dao.taxReturnHasReport(id);
        }
    }

    public List<TaxReturn> getInspectorTaxReturn(int inspectorId) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            return dao.getInspectorTaxReturn(inspectorId);
        }
    }

    public List<TaxReturn> getTaxReturnList(String currentPage, int inspectorId) {
        int currentPageInt = currentPage != null ? Integer.parseInt(currentPage) : 1;
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            currentPageInt = currentPageInt <= 0 ? 0 : (currentPageInt - 1) * postOnPage;
            return dao.getInRange(currentPageInt, postOnPage, inspectorId);
        }
    }

    public int getPageCount(int inspectorId) {
        try (JDBCTaxReturnFactory dao = daoFactory.createTaxReturn()) {
            return dao.getPageCount(inspectorId) % postOnPage == 0 ? dao.getPageCount(inspectorId) / postOnPage :
                    dao.getPageCount(inspectorId) / postOnPage + 1;
        }
    }
}
