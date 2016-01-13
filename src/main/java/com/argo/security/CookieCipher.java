package com.argo.security;

import com.argo.security.exception.CookieExpiredException;
import com.argo.security.exception.CookieInvalidException;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yamingd on 9/9/15.
 */
public class CookieCipher {

    private static Logger logger = LoggerFactory.getLogger(CookieCipher.class);

    /**
     * 加密Cookie值.
     * @param name Cookie名称
     * @param value Cookie值
     * @return String 返回结果字符串
     * @throws Exception 抛出异常
     */
    public static String encrypt(String name, String value) throws Exception {
        String secret = getCookieSecretSalt();
        long timestamp = System.currentTimeMillis() / 1000;
        value = BaseEncoding.base64Url().encode(value.getBytes("UTF-8"));
        String signature = String.format("%s|%s|%s|%s", timestamp, secret, name, value);
        signature = HashProvider.sha256(signature);
        return value+"|"+timestamp+"|"+signature;
    }

    public static String getCookieSecretSalt() {
        String val = SecurityConfig.instance.getCookie().getSecret();
        return HashProvider.md5(val);
    }

    /**
     * 解密Cookie值.
     * @param name Cookie名称
     * @param value Cookie值
     * @return String 解密结果
     * @throws CookieInvalidException Cookie不正确异常
     * @throws CookieExpiredException Cookie过期异常
     */
    public static String decrypt(String name, String value) throws CookieInvalidException, CookieExpiredException {
        if (logger.isDebugEnabled()){
            logger.debug("{}:{}", name, value);
        }
        Integer days = SecurityConfig.instance.getCookie().getAge();
        if (days==null){
            days = 30;
        }

        return decodeSignedValue(name, value, days);
    }

    public static String decodeSignedValue(String name, String value,
                                           Integer days) throws CookieInvalidException, CookieExpiredException {
        String secret = getCookieSecretSalt();
        String[] parts = value.split("\\|"); // email+timestamp+signature
        if(parts.length!=3){
            throw new CookieInvalidException(value);
        }

        String signature = String.format("%s|%s|%s|%s", parts[1], secret, name, parts[0]);
        signature = HashProvider.sha256(signature);

        if(!parts[2].equals(signature)){
            logger.error("Invalid Cookie signature: " + value);
            throw new CookieInvalidException(value);
        }

        long timestamp = Long.parseLong(parts[1]) * 1000L;
        long now = System.currentTimeMillis();

        if(timestamp < (now - days * 24 * 3600 * 1000L)){
            logger.error("Expired Cookie: " + value);
            throw new CookieExpiredException();
        }

        if(timestamp > (now + 31L * 24 * 3600 * 1000)){
            logger.error("Cookie timestamp in future; possible tampering " + value);
            throw new CookieExpiredException();
        }

        value = new String(BaseEncoding.base64Url().decode(parts[0]));
        return value;
    }

}
