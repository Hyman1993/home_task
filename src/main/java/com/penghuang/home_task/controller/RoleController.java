package com.penghuang.home_task.controller;

import com.penghuang.home_task.service.RoleService;
import com.penghuang.home_task.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping("/role")
    public ResponseEntity<String> createRole() {
        return new ResponseEntity<>("Create role Successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping("/role")
    public ResponseEntity<String> deleteRole() {

        return new ResponseEntity<>("Delete role Successfully!", HttpStatus.OK);
    }
}
