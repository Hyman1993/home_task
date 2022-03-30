package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;

public interface RoleService {

    /**
     * save role.
     */
    public Role createRole(Role role);

    /**
     * delete role.
     */
    public boolean deleteRole(Role role);

}
