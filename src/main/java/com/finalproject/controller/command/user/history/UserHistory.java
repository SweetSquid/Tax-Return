package com.finalproject.controller.command.user.history;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.HistoryService;

import javax.servlet.http.HttpServletRequest;

/**
 * Shows user's tax return history
 */
public class UserHistory implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HistoryService historyService = new HistoryService();
        String page = request.getParameter("page");
        int userId = (int) request.getSession().getAttribute("userId");

        request.setAttribute("historyList", historyService.getHistoryList(page, userId));
        request.setAttribute("currentPage", request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1);
        request.setAttribute("pageCount", historyService.getPageCount(userId));
        return "/WEB-INF/user/user-history-taxreturn.jsp";
    }
}
