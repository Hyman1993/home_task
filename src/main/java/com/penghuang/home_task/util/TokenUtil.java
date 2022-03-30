package com.penghuang.home_task.util;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.penghuang.home_task.exception.SystemException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    // expiration time: 2h
    private static long EXPIRE_DATE = 2*60*60*1000;

    //token secret
    private static String TOKEN_SECRET = "hometask";

    /**
     * generate token
     * @param username
     * @param password
     * @return
     */
    public static String token(String username,String password){

        String token = "";
        try {
            // expired time
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            // algorithm Signature
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // header
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            // payload
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username",username)
                    .withClaim("password",password).withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            throw new SystemException("generate token failed!");
        }
        return token;
    }

    /**
     * verify token
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    /**
     * get user info from token
     * @param token
     * @return
     */
    public static String getUserFromToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        }catch (Exception e){
            return  null;
        }
    }
}
