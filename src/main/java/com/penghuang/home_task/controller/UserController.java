package com.penghuang.home_task.controller;

import com.penghuang.home_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser() {
        return new ResponseEntity<>("Create user Successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser() {

        return new ResponseEntity<>("Delete user Successfully!", HttpStatus.OK);
    }

}
