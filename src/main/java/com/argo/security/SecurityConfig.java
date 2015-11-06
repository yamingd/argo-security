package com.argo.security;

import com.argo.yaml.YamlTemplate;

import java.io.IOException;

/**
 * Created by yamingd on 9/9/15.
 */
public class SecurityConfig {

    private static final String confName = "security.yaml";

    public static SecurityConfig instance = null;

    /**
     * 加载配置信息
     * @throws IOException 文件读取异常
     */
    public synchronized static void load() throws IOException {
        if (instance != null){
            return;
        }
        SecurityConfig.instance = YamlTemplate.load(SecurityConfig.class, confName);
    }

    private String passwdSalt;
    private CookieConfig cookie;
    private AESConfig aes;

    public CookieConfig getCookie() {
        return cookie;
    }

    public void setCookie(CookieConfig cookie) {
        this.cookie = cookie;
    }

    public AESConfig getAes() {
        return aes;
    }

    public void setAes(AESConfig aes) {
        this.aes = aes;
    }

    public String getPasswdSalt() {
        return passwdSalt;
    }

    public void setPasswdSalt(String passwdSalt) {
        this.passwdSalt = passwdSalt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecurityConfig{");
        sb.append("passwdSalt='").append(passwdSalt).append('\'');
        sb.append(",\n cookie=").append(cookie);
        sb.append(",\n aes=").append(aes);
        sb.append('}');
        return sb.toString();
    }
}
