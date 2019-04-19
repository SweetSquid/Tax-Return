package com.finalproject.controller.command.inspector;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.ActionReportService;
import com.finalproject.model.service.HistoryService;
import com.finalproject.model.service.TaxReturnService;
import com.finalproject.model.entity.ActionReport;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.util.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Edit implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        HistoryService historyService = new HistoryService();
        TaxReturnService taxReturnService = new TaxReturnService();
        ActionReportService actionReportService = new ActionReportService();

        request.setAttribute("editAction", taxReturnService.id(Integer.parseInt(request.getParameter("id"))).get());

        if (Utils.isNotNull(request.getParameter("message"))) {
            int taxReturnId = Integer.parseInt(request.getParameter("id"));
            ActionReport actionReport = actionReportService.create(ActionReport.Action.EDIT, request.getParameter("message"), taxReturnId);
            Optional<TaxReturn> taxReturn = taxReturnService.id(Integer.parseInt(String.valueOf(request.getAttribute("userTaxReturnId"))));
            taxReturn.ifPresent(taxReturn1 -> historyService.create(taxReturn1.getId(), taxReturn1.getUserId(),
                    ActionReport.Action.EDIT, actionReport.getMessage()));
            return "redirect:taxreturn/tax-return-list";
        }
        return "/WEB-INF/inspector/edit-report.jsp";
    }
}
