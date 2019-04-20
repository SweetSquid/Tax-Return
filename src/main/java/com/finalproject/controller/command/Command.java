package com.finalproject.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    /**
     * @param request HttpServletRequest request
     * @return path to be forwarded or redirected to Controller for dispatching
     */
    String execute(HttpServletRequest request);
}
