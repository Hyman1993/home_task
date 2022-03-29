package com.penghuang.home_task.controller;

import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Users;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if(!StringUtils.hasText(user.getName()) || !StringUtils.hasText(user.getPassword()) ) {
            throw new SystemException("username or password can't be empty!");
        }
        userService.createUser(user);
        return new ResponseEntity<>("Create user Successfully!", HttpStatus.CREATED);
    }

    /**
     * delete user.
     * @return
     */
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        if(!StringUtils.hasText(user.getName())) {
            throw new SystemException("username can't be empty!");
        }

        userService.deleteUser(user);
        return new ResponseEntity<>("Delete user Successfully!", HttpStatus.OK);
    }

    // for testing
    /**
     * delete user.
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {

        return new ResponseEntity<>(Users.users, HttpStatus.OK);
    }
}