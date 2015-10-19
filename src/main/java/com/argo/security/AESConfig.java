package com.argo.security;

/**
 * Created by yamingd on 9/9/15.
 */
public class AESConfig {

    private String seed;
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

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
        if (null != seed) {
            String md5 = HashProvider.md5(seed);
            char[] tmp = new char[16];

            for (int i = 0; i < 16; i++) {
                tmp[i] = md5.charAt(i * 2);
            }
            this.salt = new String(tmp);

            for (int i = 0; i < 16; i++) {
                tmp[i] = md5.charAt(i * 2 + 1);
            }
            this.iv = new String(tmp);
        }
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
