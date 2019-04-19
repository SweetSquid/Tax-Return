package com.finalproject.controller.command.inspector;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.ActionReport;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.ActionReportService;
import com.finalproject.model.service.HistoryService;
import com.finalproject.model.service.TaxReturnService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Approve implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        HistoryService historyService = new HistoryService();
        TaxReturnService taxReturnService = new TaxReturnService();
        ActionReportService actionReportService = new ActionReportService();

        int taxReturnId = Integer.parseInt((String) request.getAttribute("userTaxReturnId"));
        actionReportService.create(ActionReport.Action.APPROVED, null, taxReturnId);
        Optional<TaxReturn> taxReturn = taxReturnService.id(Integer.parseInt(String.valueOf(request.getAttribute("userTaxReturnId"))));

        taxReturn.ifPresent(p -> historyService.create(p.getId(), p.getUserId(),
                ActionReport.Action.APPROVED, null));
        return "redirect:taxreturn/tax-return-list";
    }
}
