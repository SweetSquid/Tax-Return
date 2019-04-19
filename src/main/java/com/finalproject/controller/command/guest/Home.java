package com.finalproject.controller.command.guest;

import com.finalproject.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class Home implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
