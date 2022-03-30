package com.penghuang.home_task.service;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Tokens;
import com.penghuang.home_task.entity.Users;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.util.MD5Util;
import com.penghuang.home_task.util.TokenUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {

    /**
     * authenticate
     * @param user
     * @return
     */
    @Override
    public String authenticate(User user) {

        // check whether the user exist
        if(!Users.isUserExist(user)) {
            throw new SystemException("generate token failed! user doesn't exist!");
        }

        User tempUser = Users.users.stream().filter(t -> user.getName().equals(t.getName())).findFirst().get();
        String pass = MD5Util.encryption(user.getPassword());

        if(!pass.equals(tempUser.getPassword())) {
            throw new SystemException("generate token failed! password isn't correct!");
        }

        // generate token
        String token = TokenUtil.token(user.getName(),user.getPassword());

        // store token
        Tokens.tokens.add(token);

        return token;
    }

    /**
     * invalidate
     * @param token
     */
    @Override
    public boolean invalidate(String token) {
        boolean checkFlg = TokenUtil.verify(token);
        if(checkFlg) {
           if(Tokens.tokens.contains(token)) {
               return Tokens.tokens.remove(token);
           } else {
               throw new SystemException("invalidate token failed! token isn't valid!");
           }
        } {
            throw new SystemException("invalidate token failed! token isn't valid!");
        }
    }

    /**
     * check whether the user identified by the token and belongs to the role
     * @param token
     * @param roleName
     * @return
     */
    @Override
    public boolean checkRole(String token,String roleName) {
        boolean checkFlg = TokenUtil.verify(token);
        if (checkFlg) {
           String userName = TokenUtil.getUserFromToken(token);
           User tempUser = Users.users.stream().filter(t -> userName.equals(t.getName())).findFirst().get();
           List<String> roles = tempUser.getRoles();
           return roles.contains(roleName);
        } else {
            throw new SystemException("check role for token failed! token isn't valid!");
        }
    }

    /**
     * get all roles for the user.
     * @param token
     * @return
     */
    @Override
    public List<String> allRoles(String token) {
        boolean checkFlg = TokenUtil.verify(token);
        if (checkFlg) {
            String userName = TokenUtil.getUserFromToken(token);
            User tempUser = Users.users.stream().filter(t -> userName.equals(t.getName())).findFirst().get();
            List<String> roles = tempUser.getRoles();
            return roles;
        } else {
            throw new SystemException("get all roles failed! token isn't valid!");
        }
    }
}
