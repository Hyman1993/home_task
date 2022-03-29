package com.penghuang.home_task.entity;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.exception.SystemException;

import java.util.ArrayList;
import java.util.List;

public class Roles {
    public static List<Role> roles = new ArrayList<>();

    /**
     * save role into roles.
     * @param role
     */
    public static void add(Role role) {
        if(isRoleExist(role)) {
            throw new SystemException("create role failed,because the role already exist!" + ", roleName:" + role.getRoleName() );
        }
        roles.add(role);
    }

    /**
     * delete role from roles.
     * @param role
     */
    public static void delete(Role role) {
        if(!isRoleExist(role)) {
            throw new SystemException("delete role failed,because the role doesn't exist!" + ", roleName:" + role.getRoleName() );
        }
        roles.removeIf(t -> role.getRoleName().equals(t.getRoleName()));
    }

    /**
     * Check where the role exist in the roles.
     * @param role
     * @return if exist,return ture, otherwise return false
     */
    private static boolean isRoleExist(Role role) {
        return roles.stream().anyMatch(t -> role.getRoleName().equals(t.getRoleName()));
    }

}
