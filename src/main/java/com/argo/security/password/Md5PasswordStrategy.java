package com.argo.security.password;

import com.argo.security.HashProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("md5PasswordStrategy")
public class Md5PasswordStrategy extends AbstractPasswordStrategy {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String encrypt(String password, String email) {
		password = HashProvider.md5(password);
		return password;
	}

	@Override
	public int getModeId() {
		return 2;
	}
}
