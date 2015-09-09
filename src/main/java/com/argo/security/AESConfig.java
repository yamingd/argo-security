package com.argo.security;

/**
 * Created by yamingd on 9/9/15.
 */
public class AESConfig {

    private String salt;
    private String iv;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AESConfig{");
        sb.append("salt='").append(salt).append('\'');
        sb.append(", iv='").append(iv).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
