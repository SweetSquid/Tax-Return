package com.finalproject.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LangFilter implements Filter {
    private Map<String, Locale> langMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        langMap.put("ua", new Locale("uk", "UA"));
        langMap.put("en", new Locale("en", "US"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String lang = servletRequest.getParameter("lang");
        if (lang != null) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Config.set(request.getSession(), Config.FMT_LOCALE, langMap.get(lang));
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
