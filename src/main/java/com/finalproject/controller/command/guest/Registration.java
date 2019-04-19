package com.finalproject.controller.command.guest;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String idCode = request.getParameter("idCode");
        String phone = request.getParameter("phone");

        request.setAttribute("fullName", fullName);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("idCode", idCode);
        request.setAttribute("phone", phone);
        UserService userService = new UserService();
        if (userService.register(request, fullName, username, email, idCode, phone, password)) {
            new Login().execute(request);
            return "redirect:taxreturn";
        }
        return "/registration.jsp";
    }
}


