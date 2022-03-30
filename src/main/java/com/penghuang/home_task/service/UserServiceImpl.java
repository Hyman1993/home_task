package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    /**
     * save user.
     */
    @Override
    public User createUser(User user) {
          Users.add(user);
          return user;
    }

    /**
     * delete user.
     */
    @Override
    public boolean deleteUser(User user) {
        Users.delete(user);
        return true;
    }

    @Override
    public void addRole2User(User user, Role role) {
        Users.addRole2User(user, role);
    }
}
