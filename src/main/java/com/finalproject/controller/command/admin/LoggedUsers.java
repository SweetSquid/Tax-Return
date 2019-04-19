package com.finalproject.controller.command.admin;

import com.finalproject.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LoggedUsers implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/admin/logged_users.jsp";
    }
}
