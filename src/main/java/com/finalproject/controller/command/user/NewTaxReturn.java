package com.finalproject.controller.command.user;

import com.finalproject.controller.command.Command;
import com.finalproject.model.service.TaxReturnService;
import com.finalproject.model.service.UserService;
import com.finalproject.model.entity.TaxReturn;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * Creates new tax return
 */
public class NewTaxReturn implements Command {
    private final static Logger LOGGER = Logger.getLogger(NewTaxReturn.class.getSimpleName());

    @Override
    public String execute(HttpServletRequest request) {
        UserService userService = new UserService();
        TaxReturnService taxReturnService = new TaxReturnService();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        request.setAttribute("taxList", TaxReturn.Category.values());

        if (request.getParameter("taxCategory") != null && userId != null) {
            TaxReturn taxReturn = new TaxReturn();
            taxReturn.setUserId(userId);

            if (!userService.userHasInspector(userId)) {
                taxReturn.setInspectorId(getRandomElement(userService.getInspectorIdList()));
            } else {
                taxReturn.setInspectorId(taxReturnService.getInspectorId(userId));
            }
            String taxCategory = request.getParameter("taxCategory");
            Double wage = Double.parseDouble(request.getParameter("wage"));
            Double militaryCollection = Double.parseDouble(request.getParameter("militaryCollection"));
            Double incomeTax = Double.parseDouble(request.getParameter("incomeTax"));

            taxReturn = taxReturnService.create(taxReturn.getUserId(), taxReturn.getInspectorId(), taxCategory,
                    wage, militaryCollection, incomeTax);
            LOGGER.info("Tax return has been created successfully");
            request.getSession().setAttribute("inspectorId", taxReturn.getInspectorId());
            return "redirect:taxreturn";
        }

        return "/WEB-INF/user/new-tax-return.jsp";
    }

    /**
     * Gets user random inspector if he does not have it
     *
     * @param list list of all existing inspector in system
     * @return random inspector id
     */
    private int getRandomElement(List<Integer> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
