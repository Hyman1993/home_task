package com.penghuang.home_task.entity;

import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.exception.SystemException;

import java.util.ArrayList;
import java.util.List;

public class Users {
    public static List<User> users = new ArrayList<>();

    /**
     * Add user to users.
     * @param user
     */
    public static void add(User user) {
        if(isUserExist(user)) {
            throw new SystemException("create user failed,because the user already exist!" + ", username:" + user.getName() );
        }
        user.setEncodePassword(user.getPassword());
        users.add(user);
    }

    /**
     * Delete user from users.
     * @param user
     */
    public static void delete(User user) {
        if(!isUserExist(user)) {
            throw new SystemException("delete user failed, because the user doesn't exist!" + ", username:" + user.getName() );
        }
        users.removeIf(t -> user.getName().equals(t.getName()));
    }

    /**
     * Check where the user exist in the users.
     * @param user
     * @return if exist,return ture, otherwise return false
     */
    private static boolean isUserExist(User user) {
        return users.stream().anyMatch(t -> user.getName().equals(t.getName()));
    }


}
