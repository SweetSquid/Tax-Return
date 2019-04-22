package com.finalproject.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Deletes user session
 */
public class Logout implements Command {
    private final static Logger LOGGER = Logger.getLogger(Logout.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        LOGGER.info("User logout successful");
        return "redirect:taxreturn";
    }
}
