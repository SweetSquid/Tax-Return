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
import java.util.ResourceBundle;

public class JDBCUserFactory implements UserDao {
    private final static Logger LOGGER = Logger.getLogger(JDBCUserFactory.class.getSimpleName());
    private Connection connection;
    private static ResourceBundle bundle = ResourceBundle.getBundle("database/queries");

    JDBCUserFactory(Connection connection) {
        this.connection = connection;
        LOGGER.debug("Creating instance of " + this.getClass().getName());
    }


    @Override
    public boolean create(User user) throws NotUniqueUsernameException, NotUniqueEmailException, NotUniqueIdCodeException, NotUniquePhoneException, SQLException {
        String queryUsername = "SELECT * FROM users WHERE username = ?";
        String queryEmail = "SELECT * FROM users WHERE email = ?";
        String queryPhone = "SELECT * FROM users WHERE phone = ?";
        String queryIdCode = "SELECT * FROM users WHERE id_code = ?";
        connection.setAutoCommit(false);
        try {
            PreparedStatement ps = connection.prepareCall(queryUsername);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOGGER.error("Not unique username: " + user.getUsername());
                throw new NotUniqueUsernameException(user.getUsername());
            }

            ps = connection.prepareCall(queryPhone);
            ps.setString(1, user.getPhone());
            rs = ps.executeQuery();
            if (rs.next()) {
                LOGGER.error("Not unique phone: " + user.getPhone());
                throw new NotUniquePhoneException(user.getPhone());
            }

            ps = connection.prepareCall(queryIdCode);
            ps.setString(1, user.getIdCode());
            rs = ps.executeQuery();
            if (rs.next()) {
                LOGGER.error("Not unique id code: " + user.getIdCode());
                throw new NotUniqueIdCodeException(user.getIdCode());
            }

            ps = connection.prepareCall(queryEmail);
            ps.setString(1, user.getEmail());
            rs = ps.executeQuery();
            if (rs.next()) {
                LOGGER.error("Not unique email: " + user.getEmail());
                throw new NotUniqueEmailException(user.getEmail());
            }
            ps.close();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(bundle.getString("user.add"))) {
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getIdCode());
            preparedStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException: user " + user.getUsername() + " wasn't registered");
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
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("user.read.id"))) {
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
        List<User> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("user.read.all"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while searching for all tax returns");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(User user, int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(bundle.getString("user.update"))) {
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getIdCode());
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Update user by id is successful");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("SQLException while updating user with id: " + id);
        }

        return false;
    }


    @Override
    public boolean delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(bundle.getString("user.delete"))) {
            statement.setInt(1, id);
            statement.executeUpdate();
            LOGGER.info("Performed delete of user with id=" + id);
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQLException trying to delete user with id=" + id, e);
            throw new RuntimeException(e);
        }
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
            LOGGER.error("SQLException while searching for user by " + type);
        }
        return result;
    }


    @Override
    public List<Integer> getInspectorIdList() {
        List<Integer> inspectorList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareCall(bundle.getString("user.find.role.inspector"))) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inspectorList.add(extractFromResultSet(rs).getId());
            }
            LOGGER.info("Search inspector id list is successful");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("SQLException while searching for inspector id list");
        }
        return inspectorList;
    }

}