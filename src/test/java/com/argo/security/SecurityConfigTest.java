package com.argo.security;

import org.junit.Test;

/**
 * Created by yamingd on 9/9/15.
 */
public class SecurityConfigTest {

    @Test
    public void testLoad() throws Exception {
        SecurityConfig.load();
        System.out.println(SecurityConfig.instance);

    }

}
