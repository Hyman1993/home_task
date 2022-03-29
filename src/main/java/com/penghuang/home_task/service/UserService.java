package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;

public interface UserService {

    /**
     * save user.
     */
    public void createUser(User user);

    /**
     * delete user.
     */
    public void deleteUser(User user);

    /**
     * add role to user.
     * @param role
     */
    void addRole2User(User user,Role role);
}
