package com.penghuang.home_task.dto;

import com.penghuang.home_task.util.MD5Util;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;

    private String password;

    private List<String> roles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncodePassword(String password) {
        String encodePasswd = MD5Util.encryption(password);
        this.password = encodePasswd;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
