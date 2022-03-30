package com.penghuang.home_task.entity;

import com.penghuang.home_task.dto.Role;
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
    public static boolean isUserExist(User user) {
        return users.stream().anyMatch(t -> user.getName().equals(t.getName()));
    }


    /**
     * Add role to user
     * @param user
     * @param role
     */
    public static void addRole2User(User user, Role role) {
         if(!Roles.isRoleExist(role)) {
             throw new SystemException("add role to user failed,because the role doesn't exist!" + ", roleName:" + role.getRoleName() );
         }

         if(!isUserExist(user)) {
             throw new SystemException("add role to user failed,because the user doesn't exist!" + ", userName:" + user.getName() );
         }

         User tempUser = users.stream().filter(t -> user.getName().equals(t.getName())).findFirst().get();
         boolean check = tempUser.getRoles().stream().anyMatch(t -> role.getRoleName().equals(t));
         if(check) {
             // do nothing
             return;
         }

        tempUser.getRoles().add(role.getRoleName());

    }
}
