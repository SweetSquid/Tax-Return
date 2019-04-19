package com.finalproject.controller.command.inspector;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.TaxReturn;
import com.finalproject.model.service.TaxReturnService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TaxReturnData implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        TaxReturnService taxReturnService = new TaxReturnService();
        String page = request.getParameter("page");
        int inspectorId = (int) request.getSession().getAttribute("userId");
        System.out.println(inspectorId);
        request.setAttribute("taxReturnList", taxReturnService.getExpoList(page, inspectorId));
        request.setAttribute("currentPage", request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1);
        request.setAttribute("pageCount", taxReturnService.getPageCount(inspectorId));
        System.out.println(taxReturnService.getPageCount(inspectorId));
        List<TaxReturn> taxReturnList = taxReturnService.getInspectorTaxReturn(inspectorId);
        request.getSession().setAttribute("taxReturnList", taxReturnList);
        return "/WEB-INF/inspector/user-tax-return.jsp";
    }
}
