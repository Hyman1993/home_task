package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.entity.Roles;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    /**
     * create role.
     * @param role
     */
    @Override
    public Role createRole(Role role) {
        Roles.add(role);
        return role;
    }

    /**
     * delete role
     * @param role
     */
    @Override
    public boolean deleteRole(Role role) {
        Roles.delete(role);
        return true;
    }

}
