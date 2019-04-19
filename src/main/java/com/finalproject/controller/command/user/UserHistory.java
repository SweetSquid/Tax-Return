package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.HistoryService;

import javax.servlet.http.HttpServletRequest;

public class UserHistory implements Command {
    //TODO сделать разные таблицы историй по кнопкам (отчёты, смена инспектора и т.д.)
    @Override
    public String execute(HttpServletRequest request) {
        HistoryService historyService = new HistoryService();
        String page = request.getParameter("page");
        int userId = (int) request.getSession().getAttribute("userId");

        request.setAttribute("historyList", historyService.getExpoList(page, userId));
        request.setAttribute("currentPage", request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1);
        request.setAttribute("pageCount", historyService.getPageCount(userId));
        return "/WEB-INF/user/user-history.jsp";
    }
}
