package com.penghuang.home_task.controller;

import com.penghuang.home_task.dto.ResponseDto;
import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Roles;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    /**
     *  create role.
     * @param role
     * @return
     */
    @PostMapping("/role")
    public ResponseEntity<ResponseDto> createRole(@RequestBody Role role) {
        if(!StringUtils.hasText(role.getRoleName())) {
            throw new SystemException("roleName can't be empty!");
        }
        roleService.createRole(role);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(),"Create role Successfully!",null), HttpStatus.CREATED);
    }

    /**
     * delete role.
     * @param role
     * @return
     */
    @DeleteMapping("/role")
    public ResponseEntity<ResponseDto> deleteRole(@RequestBody Role role) {
        if(!StringUtils.hasText(role.getRoleName())) {
            throw new SystemException("roleName can't be empty!");
        }
        roleService.deleteRole(role);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),"Delete role Successfully!",null), HttpStatus.OK);
    }

    // for testing
    /**
     * get users.
     * @return
     */
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return new ResponseEntity<>(Roles.roles, HttpStatus.OK);
    }
}
