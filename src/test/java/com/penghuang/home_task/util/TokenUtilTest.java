package com.penghuang.home_task.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class for TokenUtil
 */
public class TokenUtilTest {

    /**
     * test TokenUtil method.
     * case: if token valid, then true, if token isn't valid, then false.
     *
     */
    @Test
    public void testToken_Ok(){
        String username = "tom";
        String password = "a123";

        String resultToken = TokenUtil.token(username,password);

        assertNotNull(resultToken);

        Boolean checkResult = TokenUtil.verify(resultToken);
        assertTrue(checkResult);

        String expiredToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImExMjMiLCJleHAiOjE2NDg2NDA1NTAsInVzZXJuYW1lIjoidG9tIn1.AVKmtNdUBonRBDf5mgpzZiVe2peFhqwqO8NAupEH7ek";
        Boolean checkResultOld = TokenUtil.verify(expiredToken);
        assertFalse(checkResultOld);
    }

}
