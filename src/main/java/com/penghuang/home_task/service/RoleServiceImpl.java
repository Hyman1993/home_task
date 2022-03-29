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
    public void createRole(Role role) {
        Roles.add(role);
    }

    /**
     * delete role
     * @param role
     */
    @Override
    public void deleteRole(Role role) {
        Roles.delete(role);
    }

}
