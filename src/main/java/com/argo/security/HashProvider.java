package com.argo.security;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yamingd on 9/9/15.
 */
public class HashProvider {

    private static final String HMAC_SHA1 = "HmacSHA256";

    /**
     * md5加密
     * @param value 明文文本
     * @return String HASH后的文本
     */
    public static String md5(String value){
        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher().putString(value, Charsets.UTF_8).hash();
        return hc.toString();
    }

    public static String md5ASCII(String value){
        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher().putString(value, Charsets.US_ASCII).hash();
        return hc.toString();
    }

    /**
     * SHA256
     * @param data 明文文本
     * @return String 摘要文本
     */
    public static String sha256(String data){
        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.newHasher().putString(data, Charsets.UTF_8).hash();
        return hc.toString();
    }

    /**
     * HMAC 256 with UTF-8 encoding
     * @param data 明文文本
     * @param secret 加密私钥
     * @return String 摘要文本
     */
    public static String hmac(String data, String secret) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance(HMAC_SHA1);
            SecretKeySpec spec;
            String oauthSignature = uriEncode(secret) + "&";
            spec = new SecretKeySpec(oauthSignature.getBytes(Charsets.UTF_8), HMAC_SHA1);
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes(Charsets.UTF_8));
            return BaseEncoding.base64Url().encode(byteHMAC);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException ignore) {
            // should never happen
            return null;
        }
    }

    /**
     * @param value string to be encoded
     * @return encoded string
     * @see <a href="http://wiki.oauth.net/TestCases">OAuth / TestCases</a>
     * @see <a href="http://groups.google.com/group/oauth/browse_thread/thread/a8398d0521f4ae3d/9d79b698ab217df2?hl=en&lnk=gst&q=space+encoding#9d79b698ab217df2">Space encoding - OAuth | Google Groups</a>
     * @see <a href="http://tools.ietf.org/html/rfc3986#section-2.1">RFC 3986 - Uniform Resource Identifier (URI): Generic Syntax - 2.1. Percent-Encoding</a>
     */
    public static String uriEncode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length()
                    && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
    }
}
