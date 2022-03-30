package com.penghuang.home_task.controller;

import com.penghuang.home_task.dto.ResponseDto;
import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.Role2User;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Users;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * create user.
     * @param user
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity<ResponseDto> createUser(@RequestBody User user) {
        if(!StringUtils.hasText(user.getName()) || !StringUtils.hasText(user.getPassword()) ) {
            throw new SystemException("username or password can't be empty!");
        }
        userService.createUser(user);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(),"Create user Successfully!",null), HttpStatus.CREATED);
    }

    /**
     * delete user.
     * @return
     */
    @DeleteMapping("/user")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody User user) {
        if(!StringUtils.hasText(user.getName())) {
            throw new SystemException("username can't be empty!");
        }

        userService.deleteUser(user);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),"Delete user Successfully!",null), HttpStatus.OK);
    }

    /**
     * add role to user
     * @param role2User
     * @return
     */
    @PutMapping("/role2user")
    public ResponseEntity<ResponseDto> addRole2User(@RequestBody Role2User role2User) {
        Role role = role2User.getRole();
        User user = role2User.getUser();
        if(!StringUtils.hasText(role.getRoleName())) {
            throw new SystemException("roleName can't be empty!");
        }
        if(!StringUtils.hasText(user.getName())) {
            throw new SystemException("userName can't be empty!");
        }
        userService.addRole2User(user,role);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),"Add role to user Successfully!",null), HttpStatus.OK);
    }

    // for testing
    /**
     * get users.
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {

        return new ResponseEntity<>(Users.users, HttpStatus.OK);
    }
}
