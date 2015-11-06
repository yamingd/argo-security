package com.argo.security;

import com.argo.security.exception.PermissionDeniedException;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求校验
 * Created by yamingd on 9/9/15.
 */
public class RequestCipher {

    private static Logger logger = LoggerFactory.getLogger(RequestCipher.class);

    /**
     * 检查请求签名是否有效
     * @param url 访问url
     * @param userId 当前用户标示
     * @param sign0 请求的签名
     * @throws PermissionDeniedException 抛出访问拒接异常
     */
    public static void verify(String url, String userId, String sign0)
            throws PermissionDeniedException {

        CookieConfig cookie = SecurityConfig.instance.getCookie();
        String cookieId = cookie.getName();
        String cookieSecret = cookie.getSecret();

        String hexKey = sign0.substring(0, 10);
        sign0 = sign0.substring(10);

        StringBuilder plain = new StringBuilder();
        plain.append(HashProvider.md5(hexKey));
        plain.append("|").append(userId)
                .append("|").append(url)
                .append("|").append(cookieSecret)
                .append("|").append(cookieId);

        //logger.debug("plain: {}", plain.toString());

        String sign1 = digest(plain.toString());

        if (!sign0.equals(sign1)){

            logger.error("Error Sign. cookieId={}, cookieSecret={}, url={}, key={}, userId={}, server sign={}, client sign={}",
                    cookieId, cookieSecret, url, hexKey, userId, sign1, sign0);

            throw new PermissionDeniedException("Request Denied. " + url);
        }

    }

    private static String digest(String text){

        byte[] data = text.getBytes(Charsets.US_ASCII);

        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.newHasher().putBytes(data).hash();
        String sign1 = hc.toString();

        return sign1;
    }

    /**
     * 对请求做签名
     * @param url 访问URL
     * @param userId 访问用户标示
     * @return String 签名结果
     */
    public static String sign(String url, String userId){

        CookieConfig cookie = SecurityConfig.instance.getCookie();
        String cookieId = cookie.getName();
        String cookieSecret = cookie.getSecret();

        String hexkey = RandomStringUtils.randomAlphanumeric(10);

        StringBuilder plain = new StringBuilder();
        plain.append(HashProvider.md5ASCII(hexkey));
        plain.append("|").append(userId)
                .append("|").append(url)
                .append("|").append(cookieSecret)
                .append("|").append(cookieId);

        String ret = digest(plain.toString());
        return hexkey + ret;

    }


}
