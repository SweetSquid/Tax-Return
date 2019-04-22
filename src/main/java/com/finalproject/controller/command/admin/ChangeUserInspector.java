package com.finalproject.controller.command.admin;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.ChangeInspectorReport;
import com.finalproject.model.service.ChangeInspectorReportService;
import com.finalproject.model.service.UserService;
import com.finalproject.model.service.util.Utils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Changes user's inspector by request
 */
public class ChangeUserInspector implements Command {
    private final static Logger LOGGER = Logger.getLogger(ChangeUserInspector.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        UserService userService = new UserService();
        ChangeInspectorReportService changeInspectorReportService = new ChangeInspectorReportService();
        List<ChangeInspectorReport> changeInspectorList = changeInspectorReportService.readAll();
        List<Integer> inspectorList = userService.getInspectorIdList();
        request.setAttribute("inspectorList", inspectorList);
        if (Utils.isNotNull(request.getParameter("id"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            ChangeInspectorReport changeInspectorReport = changeInspectorReportService.id(id);
            changeInspectorList.remove(changeInspectorReport);
            changeInspectorReport.setNewInspectorId(Integer.parseInt(request.getParameter("newInspectorId")));
            changeInspectorReport.setMessage("");
            changeInspectorReport.setStatus(ChangeInspectorReport.Status.APPROVED);
            changeInspectorReportService.update(changeInspectorReport, id);
            LOGGER.info("Successful change of user's inspector from " + changeInspectorReport.getPreviousInspectorId() +
                    " to " + changeInspectorReport.getNewInspectorId());
        }
        request.setAttribute("changeInspectorList", changeInspectorList);

        return "/WEB-INF/admin/change-user-inspector.jsp";
    }
}
