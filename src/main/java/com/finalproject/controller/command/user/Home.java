package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCTaxReturnFactory;

import javax.servlet.http.HttpServletRequest;

public class Home implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("inspectorId") == null) {
            JDBCTaxReturnFactory jdbcTaxReturnFactory = DaoFactory.getInstance().createTaxReturn();
            Integer inspectorId = jdbcTaxReturnFactory.getInspectorId((Integer) request.getSession().getAttribute("userId"));
            request.getSession().setAttribute("inspectorId", inspectorId);
            jdbcTaxReturnFactory.close();
        }
        return "/WEB-INF/user/index.jsp";
    }
}