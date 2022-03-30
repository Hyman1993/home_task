package com.penghuang.home_task.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * test class for MD5Util
 */
public class MD5UtilTest {
    /**
     * test MD5Util method.
     * case: if password is encrypted then true, if token isn't valid, then false.
     *
     */
    @Test
    public void testToken_Ok(){
        String password = "a123";
        String result = MD5Util.encryption(password);
        assertNotNull(result);
        assertEquals(32,result.length());
    }
}
