package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.controller.command.guest.Login;
import com.finalproject.model.service.ChangeInspectorReportService;
import com.finalproject.model.entity.ChangeInspectorReport;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeInspector implements Command {
    private final static Logger LOGGER = Logger.getLogger(Login.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute("userId");
        ChangeInspectorReportService service = new ChangeInspectorReportService();

        if (request.getSession().getAttribute("inspectorId") == null) {
            return "error";
        } else if (service.checkExistence(userId)) {
            LOGGER.error("User is trying to create another request for a change of inspector.");
            request.setAttribute("alreadyExist", true);
        } else if (request.getParameter("message") != null) {
            int prevInspectorId = (Integer) request.getSession().getAttribute("inspectorId");
            String message = request.getParameter("message");
            ChangeInspectorReport.Status status = ChangeInspectorReport.Status.CHANGE;
            service.create(userId, prevInspectorId, message, status);
            LOGGER.info("User create request for a change of inspector.");
            return "redirect:taxreturn/new-tax-return";
        }
        return "/WEB-INF/user/change-inspector.jsp";
    }
}
