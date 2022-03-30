package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;

import java.util.List;

public interface ValidationService {

    /**
     * authentication
     * @return
     */
    public String authenticate(User user);

    /**
     * invalidate expired token
     * @param token
     */
    public void invalidate(String token);

    /**
     * check role
     * @return
     */
    public boolean checkRole(String token,String roleName);

    /**
     * get all roles by token
     * @return
     */
    public List<String> allRoles(String token);

}
