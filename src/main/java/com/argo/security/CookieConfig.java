package com.argo.security;

/**
 * Created by yamingd on 9/9/15.
 */
public class CookieConfig {

    /**
     *
     */
    private String domain;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String sessionid;
    /**
     *
     */
    private Boolean secure;
    /**
     * 单位为天
     */
    private Integer age;
    /**
     *
     */
    private String secret;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CookieConfig{");
        sb.append("domain='").append(domain).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sessionid='").append(sessionid).append('\'');
        sb.append(", secure=").append(secure);
        sb.append(", age=").append(age);
        sb.append(", secret='").append(secret).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
