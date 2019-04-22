package com.finalproject.controller.command.user.history;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.ChangeInspectorReportService;

import javax.servlet.http.HttpServletRequest;


/**
 * Shows user's changing inspector history
 */
public class ChangeInspectorHistory implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        ChangeInspectorReportService service = new ChangeInspectorReportService();
        int userId = (int) request.getSession().getAttribute("userId");

        String page = request.getParameter("page");

        request.setAttribute("reportList", service.getChangeList(page, userId));
        request.setAttribute("currentPage", request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1);
        request.setAttribute("pageCount", service.getPageCount(userId));

        return "/WEB-INF/user/user-history-change-inspector.jsp";
    }
}
