package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.TaxReturnService;
import com.finalproject.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class Home implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        UserService userService = new UserService();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userService.userHasInspector(userId) && request.getSession().getAttribute("inspectorId") == null) {
            TaxReturnService service = new TaxReturnService();
            Integer inspectorId = service.getInspectorId(userId);
            request.getSession().setAttribute("inspectorId", inspectorId);
        }
        return "/WEB-INF/user/index.jsp";
    }
}