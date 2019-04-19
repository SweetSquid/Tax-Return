package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.ActionReportService;
import com.finalproject.model.service.TaxReturnService;

import javax.servlet.http.HttpServletRequest;

public class Edit implements Command {
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

        if (taxCategory != null && wage != null && militaryCollection != null && incomeTax != null) {
            int editActionId = Integer.parseInt(String.valueOf(request.getSession().getAttribute("editActionReturnId")));
            taxReturn = taxReturnService.edit(editActionId, taxCategory, wage, militaryCollection, incomeTax);
            System.out.println(taxReturn);
            if (taxReturnService.taxReturnHasReport(taxReturn.getId())) {
                actionReportService.delete(editActionId);
            }
            request.getSession().setAttribute("editActionReturnId", null);
            return "redirect:taxreturn";
        }
        return "/WEB-INF/user/edit-user-tax-return.jsp";
    }
}
