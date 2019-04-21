package dao;

import com.finalproject.model.dao.DaoFactory;
import com.finalproject.model.dao.impl.JDBCUserFactory;
import com.finalproject.model.entity.User;
import com.finalproject.model.exception.NotUniqueUsernameException;
import com.finalproject.model.service.encryption.JBCrypt;
import com.finalproject.model.service.encryption.PasswordService;
import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JDBCUserFactoryTest {
    private JDBCUserFactory factory;
    private PasswordService service;


    @Before
    public void init() {
        factory = DaoFactory.getInstance().createUser();
        service = new JBCrypt();
    }


    @Test
    public void createTest() {
        User user = new User();
        user.setUsername("createtest");
        user.setIdCode("13125312");
        user.setPassword(service.createHash("1"));
        user.setPhone("+380675236978");
        user.setRole(User.Role.USER);
        user.setFullname("Tomas");
        user.setEmail("email@asasd.com");
        try {
            assertTrue(factory.create(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void updateTest() {
        User user = new User();
        user.setUsername("create2test");
        user.setIdCode("13213121");
        user.setPassword(service.createHash("1"));
        user.setPhone("+380671236978");
        user.setRole(User.Role.USER);
        user.setFullname("Tomas");
        user.setEmail("email@a1sasd.com");
        try {
            assertTrue(factory.create(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User userUpd = new User();
        userUpd.setUsername("updatetest");
        userUpd.setIdCode("31131311");
        userUpd.setPassword(service.createHash("1"));
        userUpd.setPhone("+380623236278");
        userUpd.setRole(User.Role.USER);
        userUpd.setFullname("Tomas2");
        userUpd.setEmail("email@1211asasd.com");
        int id = factory.findByType("username", "create2Test").get().getId();
        assertTrue(factory.update(userUpd, id));
    }

    @Test
    public void deleteTest() {
        assertTrue(factory.delete(factory.findByType("username", "createtest").get().getId()));
    }

    @Test(expected = NotUniqueUsernameException.class)
    public void createNotUniqueEmailTest() {
        boolean result = false;
        User user = new User();
        user.setUsername("username");
        user.setIdCode("13125312");
        user.setPassword("1");
        user.setPhone("+380675236978");
        user.setRole(User.Role.USER);
        user.setFullname("Tomas");
        try {
            result = factory.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertFalse(result);
        factory.close();
    }

    @Test
    public void getInspectorIdListTest() {
        Assert.assertNotNull(factory.getInspectorIdList());
    }

    @After
    public void after() {
        factory.close();
    }

}
