package com.finalproject.controller;


import com.finalproject.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private HashSet<String> loggedUsers = new HashSet<>();

    public void init() {
        getServletContext().setAttribute("loggedUsers", loggedUsers);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @SuppressWarnings("unchecked")
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String logout = (String) request.getSession().getAttribute("logout");

        if (logout != null && logout.equals("false")) {
            loggedUsers.add((String) request.getSession().getAttribute("loggedUser"));
        }

        if (request.getParameter("editActionId") != null) {
            request.getSession().setAttribute("editActionReturnId", request.getParameter("editActionId"));
        }
        path = path.replaceAll(".*/taxreturn/", "");
        Map<String, Command> commands = (Map<String, Command>) request.getSession().getAttribute("commands");
        Command command = commands.getOrDefault(path,
                (r) -> "redirect:error");
        String page = command.execute(request);

        if (page.contains("redirect")) {
            response.sendRedirect(page.replace("redirect:", "/"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }

    }
}
