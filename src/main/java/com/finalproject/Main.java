package com.finalproject;

import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCUserFactory;
import com.finalproject.model.entity.User;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    final static Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) {
        try (JDBCUserFactory factory = DaoFactory.getInstance().createUser()) {
            User user = new User();
            user.setRole(User.Role.USER);
            user.setFullname("fullname");
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setPhone("+380135476589");
            user.setPassword("1");
            user.setIdCode("12332112");
            try {
                factory.create(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
