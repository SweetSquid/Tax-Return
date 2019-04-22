package com.finalproject.controller.command.guest;

import com.finalproject.controller.command.Command;
import com.finalproject.model.entity.User;
import com.finalproject.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Sign in user if all data is correct
 */
public class Login implements Command {
    private Map<String, String> users = new HashMap<>();
    private final static Logger LOGGER = Logger.getLogger(Login.class.getSimpleName());
    private UserService userService = new UserService();

    public Login() {
        users.put("login", "index.jsp");
        users.put("ADMIN", "redirect:taxreturn");
        users.put("INSPECTOR", "redirect:taxreturn");
        users.put("USER", "redirect:taxreturn");
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        request.setAttribute("username", username);

        if (username == null || password == null ||
                username.equals("") || password.equals("")) {
            return "/login.jsp";
        }

        if (userService.signIn(username, password)) {
            LOGGER.info("User " + username + " sign in successful");
            Optional<User> user = userService.username(username);
            if (user.isPresent()) {
                request.getSession().setAttribute("loggedUser", username.toLowerCase());
                request.getSession().setAttribute("logout", "false");
                request.getSession().setAttribute("userId", user.get().getId());
                request.getSession().setAttribute("role", user.get().getRole().toString());
                request.getSession().setAttribute("username", user.get().getUsername());
                request.getSession().setAttribute("fullname", user.get().getFullName());
                return users.getOrDefault(user.get().getRole().toString(), users.get("login"));
            }
        } else {
            request.setAttribute("wrongLogin", false);
            LOGGER.error("Attempt to sign in: wrong username or password");
        }
        return "/login.jsp";
    }

    public void setService(UserService service) {
        userService = service;
    }
}
