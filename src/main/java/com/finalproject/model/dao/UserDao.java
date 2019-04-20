package com.finalproject.model.dao;

import com.finalproject.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * UserDao is an interface that sets methods to implement in JDBCUserFactory.
 */
public interface UserDao extends GenericDao<User> {

    /**
     * The method returns user by looking type.
     *
     * @param type  type for search.
     * @param value value for search in db.
     * @return {@code Optional<User>} user.
     */
    Optional<User> findByType(String type, String value);

    /**
     * The method returns all inspectors id.
     *
     * @return {@code List<Integer>} list of all inspectors id.
     */
    List<Integer> getInspectorIdList();
}
