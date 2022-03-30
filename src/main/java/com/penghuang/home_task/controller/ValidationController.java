package com.penghuang.home_task.controller;

import com.penghuang.home_task.dto.ResponseDto;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Tokens;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ValidationController {

    private ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * autenticate user.
     * @param user
     * @return
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto> generateToken(@RequestBody User user) {
        if(!StringUtils.hasText(user.getName()) || !StringUtils.hasText(user.getPassword()) ) {
            throw new SystemException("generate token failed! username or password can't be empty!");
        }

        String token = validationService.authenticate(user);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(),"generate token successfully!",token), HttpStatus.CREATED);
    }

    /**
     * invalidate token
     * @param token
     * @return
     */
    @DeleteMapping("/invalidate")
    public ResponseEntity<ResponseDto> invalidateToken(@RequestParam(value="token", required = true) String token) {

        validationService.invalidate(token);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),"invalidate token successfully!",null), HttpStatus.OK);
    }

    /**
     * check role by token and role.
     * @param token
     * @param roleName
     * @return
     */
    @GetMapping("/checkrole")
    public ResponseEntity<ResponseDto> checkRole(@RequestParam(value="token", required = true) String token,@RequestParam(value="roleName", required = true) String roleName) {

        boolean result = validationService.checkRole(token,roleName);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),"check role from token successfully!",result), HttpStatus.OK);
    }

    /**
     * get all roles from token.
     * @param token
     * @return
     */
    @GetMapping("/allRoles")
    public ResponseEntity<ResponseDto> allRoles(@RequestParam(value="token", required = true) String token) {

        List<String> roles = validationService.allRoles(token);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),"get all roles from token successfully!",roles), HttpStatus.OK);
    }

}
