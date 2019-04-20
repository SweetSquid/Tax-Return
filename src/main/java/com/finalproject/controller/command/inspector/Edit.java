package com.finalproject.controller.command.inspector;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.ActionReport;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.ActionReportService;
import com.finalproject.model.service.HistoryService;
import com.finalproject.model.service.TaxReturnService;
import com.finalproject.model.service.util.Utils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Edit implements Command {
    private final static Logger LOGGER = Logger.getLogger(Edit.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        HistoryService historyService = new HistoryService();
        TaxReturnService taxReturnService = new TaxReturnService();
        ActionReportService actionReportService = new ActionReportService();

        request.setAttribute("editAction", taxReturnService.id(Integer.parseInt(request.getParameter("id"))).get());

        if (Utils.isNotNull(request.getParameter("message"))) {
            int taxReturnId = Integer.parseInt(request.getParameter("id"));
            ActionReport actionReport = actionReportService.create(ActionReport.Action.EDIT, request.getParameter("message"), taxReturnId);
            Optional<TaxReturn> taxReturn = taxReturnService.id(taxReturnId);
            taxReturn.ifPresent(taxReturn1 -> historyService.create(taxReturn1.getId(), taxReturn1.getUserId(),
                    ActionReport.Action.EDIT, actionReport.getMessage()));
            LOGGER.info("Tax return with id " + taxReturnId + " has been sent for edit");
            return "redirect:taxreturn/tax-return-list";
        }
        return "/WEB-INF/inspector/edit-report.jsp";
    }
}
