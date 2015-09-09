package com.argo.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yamingd on 9/9/15.
 */
public class CookieCipherTest {

    @Before
    public void setUp() throws Exception {
        SecurityConfig.load();
    }

    @Test
    public void testEncrypt() throws Exception {
        String name = SecurityConfig.instance.getCookie().getName();
        String value = "101";

        String sign = CookieCipher.encrypt(name, value);
        System.out.println(sign);
    }

    @Test
    public void testDecrypt() throws Exception {

        String name = SecurityConfig.instance.getCookie().getName();
        String value = "1010";

        String sign = CookieCipher.encrypt(name, value);
        System.out.println(sign);

        String value0 = CookieCipher.decrypt(name, sign);
        System.out.println(value0);

        Assert.assertEquals("decrypt failed", value0, value);
    }
}
