package com.argo.security;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by yamingd on 9/9/15.
 */
public class RequestCipherTest {

    @Before
    public void setUp() throws Exception {
        SecurityConfig.load();
    }

    @Test
    public void testVerify() throws Exception {

        String url = "/test/a0/b0/c0";
        String userId = "12345";

        String sign = RequestCipher.sign(url, userId);
        System.out.println(sign);

        RequestCipher.verify(url, userId, sign);

        RequestCipher.verify(url, userId, sign + "abc");
    }

    @Test
    public void testSign() throws Exception {

        String url = "/test/a/b/c";
        String userId = "1234";

        String sign = RequestCipher.sign(url, userId);
        System.out.println(sign);

    }
}
