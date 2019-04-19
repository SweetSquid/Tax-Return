package com.finalproject.controller.command.user.history;

import com.finalproject.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class HistoryChoose implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        //TODO create history jsp
        return "/WEB-INF/user/user-history.jsp";
    }
}
