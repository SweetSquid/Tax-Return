package com.finalproject.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent session) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent session) {
        HashSet<String> loggedUsers = (HashSet<String>) session.getSession()
                .getServletContext().getAttribute("loggedUsers");
        String username = (String) session.getSession().getAttribute("username");
        loggedUsers.remove(username);
        session.getSession().setAttribute("logout", "true");
        session.getSession().setAttribute("loggedUser", null);
        session.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
    }
}

