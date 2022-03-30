package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;

public interface UserService {

    /**
     * save user.
     */
    public User createUser(User user);

    /**
     * delete user.
     */
    public boolean deleteUser(User user);

    /**
     * add role to user.
     * @param role
     */
    void addRole2User(User user,Role role);
}
