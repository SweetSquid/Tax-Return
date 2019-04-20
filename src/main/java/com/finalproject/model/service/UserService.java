package com.finalproject.model.service;

import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.UserDao;
import com.finalproject.model.dao.impl.JDBCUserFactory;
import com.finalproject.model.entity.User;
import com.finalproject.model.exception.NotUniqueEmailException;
import com.finalproject.model.exception.NotUniqueIdCodeException;
import com.finalproject.model.exception.NotUniquePhoneException;
import com.finalproject.model.exception.NotUniqueUsernameException;
import com.finalproject.model.service.encryption.JBCrypt;
import com.finalproject.model.service.encryption.PasswordService;
import com.finalproject.model.service.util.Utils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final static Logger LOGGER = Logger.getLogger(UserService.class.getSimpleName());

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public boolean userHasInspector(int userId) {
        return !daoFactory.createTaxReturn().getUserTaxReturn(userId).isEmpty();
    }

    public Optional<User> username(String username) {
        Optional<User> result;
        try (UserDao userDao = daoFactory.createUser()) {
            result = userDao.findByType("username", username);
        }
        return result;
    }

    public boolean signIn(String username, String password) {
        PasswordService service = new JBCrypt();
        Optional<User> user = username(username);
        return user.filter(user1 -> service.checkPassword(password, user1.getPassword())).isPresent();
    }

    public List<Integer> getInspectorIdList() {
        try (JDBCUserFactory dao = daoFactory.createUser()) {
            return dao.getInspectorIdList();
        }
    }

    public boolean register(HttpServletRequest request, String fullName,
                            String username, String email, String idCode, String phone, String password) {
        PasswordService service = new JBCrypt();
        if (!Utils.isNotNull(fullName, username, email, password, idCode, phone)) {
            return false;
        } else {
            try (JDBCUserFactory factory = daoFactory.createUser()) {
                User user = new User();
                user.setRole(User.Role.USER);
                user.setFullname(fullName);
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(service.createHash(password));
                user.setPhone(phone);
                user.setIdCode(idCode);
                factory.create(user);
                LOGGER.info("User " + username + " has registered successfully");
                return true;
            } catch (NotUniqueUsernameException | NotUniquePhoneException | NotUniqueEmailException | NotUniqueIdCodeException e) {
                request.setAttribute("notUnique", e.getMessage());
                LOGGER.error("Not unique data at registration");
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
