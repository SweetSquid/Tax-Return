package com.finalproject.model.dao;

import com.finalproject.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByType(String type, String value);

    List<Integer> getInspectorIdList();
}
