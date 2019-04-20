package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.TaxReturnService;

import javax.servlet.http.HttpServletRequest;

public class Home implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("inspectorId") == null) {
            TaxReturnService service = new TaxReturnService();
            Integer inspectorId = service.getInspectorId((Integer) request.getSession().getAttribute("userId"));
            request.getSession().setAttribute("inspectorId", inspectorId);
        }
        return "/WEB-INF/user/index.jsp";
    }
}