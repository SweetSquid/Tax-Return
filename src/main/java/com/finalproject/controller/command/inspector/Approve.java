package com.finalproject.controller.command.inspector;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.ActionReport;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.ActionReportService;
import com.finalproject.model.service.HistoryService;
import com.finalproject.model.service.TaxReturnService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Approve implements Command {
    private final static Logger LOGGER = Logger.getLogger(Approve.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        HistoryService historyService = new HistoryService();
        TaxReturnService taxReturnService = new TaxReturnService();
        ActionReportService actionReportService = new ActionReportService();
        int taxReturnId = Integer.parseInt(request.getParameter("id"));
        actionReportService.create(ActionReport.Action.APPROVED, null, taxReturnId);
        Optional<TaxReturn> taxReturn = taxReturnService.id(taxReturnId);
        LOGGER.info("Tax return with id " + taxReturnId + " has been approved");

        taxReturn.ifPresent(p -> historyService.create(p.getId(), p.getUserId(),
                ActionReport.Action.APPROVED, null));
        return "redirect:taxreturn/tax-return-list";
    }
}
