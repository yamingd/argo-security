package com.argo.security.password;

import com.argo.security.HashProvider;
import com.argo.security.SecurityConfig;
import org.springframework.stereotype.Service;


@Service("defaultPasswordStrategy")
public class DefaultPasswordStrategy extends AbstractPasswordStrategy {

	public static int NS_MODE_ID = 1;
	
	@Override
	public String encrypt(String password, String email) {
		email = email.trim().toLowerCase();
		password = HashProvider.hmac(password, email + getPasswordSecretSalt());
		return password;
	}

	/**
	 * @return
	 */
	private String getPasswordSecretSalt() {
        String secret = SecurityConfig.instance.getPasswdSalt();
		return HashProvider.md5(secret);
	}

	@Override
	public int getModeId() {
		return NS_MODE_ID;
	}

}
