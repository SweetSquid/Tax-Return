package com.finalproject.model.dao.impl;

import com.finalproject.model.dao.UserDao;
import com.finalproject.model.entity.User;
import com.finalproject.model.exception.NotUniqueEmailException;
import com.finalproject.model.exception.NotUniqueIdCodeException;
import com.finalproject.model.exception.NotUniquePhoneException;
import com.finalproject.model.exception.NotUniqueUsernameException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserFactory implements UserDao {
    private final static Logger LOGGER = Logger.getLogger(JDBCUserFactory.class.getSimpleName());
    private Connection connection;

    JDBCUserFactory(Connection connection) {
        this.connection = connection;
        LOGGER.debug("Creating instance of " + this.getClass().getName());
    }


    @Override
    public boolean create(User user) throws NotUniqueUsernameException, NotUniqueEmailException, NotUniqueIdCodeException, NotUniquePhoneException {
        if (findByType("username", user.getUsername()).isPresent()) {
            LOGGER.error("attempt to register failed, non-unique " + user.getUsername());
            throw new NotUniqueUsernameException(user.getUsername());
        }
        if (findByType("email", user.getEmail()).isPresent()) {
            LOGGER.error("attempt to register failed, non-unique " + user.getEmail());

            throw new NotUniqueEmailException(user.getEmail());
        }
        if (findByType("phone", user.getPhone()).isPresent()) {
            LOGGER.error("attempt to register failed, non-unique " + user.getPhone());
            throw new NotUniquePhoneException(user.getPhone());
        }
        if (findByType("id_code", user.getIdCode()).isPresent()) {
            LOGGER.error("attempt to register failed, non-unique " + user.getIdCode());
            throw new NotUniqueIdCodeException(user.getIdCode());
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSQL.ADD.QUERY);
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getIdCode());
            preparedStatement.execute();
            LOGGER.info("User " + user.getUsername() + " registered successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setRole(User.Role.valueOf(rs.getString("role")));
        user.setFullname(rs.getString("fullname"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setIdCode(rs.getString("id_code"));
        return user;
    }

    @Override
    public User readId(int id) {
        User user = new User();
        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM users WHERE role = 'INSPECTOR'")) {
            ResultSet rs = ps.executeQuery();
            user = extractFromResultSet(rs);
            LOGGER.info("Search user by id is successful");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("SQLException while searching for user by id");
        }
        return user;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public boolean update(User user, int id) {
        return false;
    }


    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<User> findByType(String type, String value) {

        Optional<User> result = Optional.empty();

        String query = "SELECT * FROM users WHERE " + type + " = ?";

        try (PreparedStatement ps = connection.prepareCall(query)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = Optional.of(extractFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public List<Integer> getInspectorIdList() {
        List<Integer> inspectorList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM users WHERE role = 'INSPECTOR'")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inspectorList.add(extractFromResultSet(rs).getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inspectorList;
    }


    enum UserSQL {

        ADD("INSERT INTO users (id, role, fullname, username, email, password, phone, id_code) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)");

        String QUERY;

        UserSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}