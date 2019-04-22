package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.ActionReportService;
import com.finalproject.model.service.TaxReturnService;
import com.finalproject.model.service.util.Utils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Sends edited tax return
 */
public class Edit implements Command {
    private final static Logger LOGGER = Logger.getLogger(Edit.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        TaxReturnService taxReturnService = new TaxReturnService();
        ActionReportService actionReportService = new ActionReportService();

        String taxCategory = request.getParameter("taxCategory");
        String wage = request.getParameter("wage");
        String militaryCollection = request.getParameter("militaryCollection");
        String message = actionReportService.id(Integer.parseInt(String.valueOf(request.getSession()
                .getAttribute("editActionReturnId")))).getMessage();
        TaxReturn taxReturn = taxReturnService.getTaxReturnByActionId(Integer.parseInt(String.valueOf(request.getSession().getAttribute("editActionReturnId"))));

        request.setAttribute("taxList", TaxReturn.Category.values());
        request.setAttribute("message", message);
        request.setAttribute("taxReturn", taxReturn);
        String incomeTax = request.getParameter("incomeTax");

        if (Utils.isNotNull(taxCategory, wage, militaryCollection, incomeTax)) {
            int editActionId = Integer.parseInt(String.valueOf(request.getSession().getAttribute("editActionReturnId")));
            taxReturn = taxReturnService.edit(editActionId, taxCategory, wage, militaryCollection, incomeTax);
            if (taxReturnService.taxReturnHasReport(taxReturn.getId())) {
                actionReportService.delete(editActionId);
            }
            request.getSession().setAttribute("editActionReturnId", null);
            LOGGER.info("Tax return " + taxReturn.getId() + " has been edited successfully");
            return "redirect:taxreturn";
        }
        return "/WEB-INF/user/edit-user-tax-return.jsp";
    }
}
