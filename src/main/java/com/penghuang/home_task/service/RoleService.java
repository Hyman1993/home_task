package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;

public interface RoleService {

    /**
     * save role.
     */
    public void createRole(Role role);

    /**
     * delete role.
     */
    public void deleteRole(Role role);

}
