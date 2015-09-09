package com.argo.security.password;

import com.argo.security.HashProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ucPasswordStrategy")
public class UCPasswordStrategy extends AbstractPasswordStrategy {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String encrypt(String password, String salt) {
		password = password.toLowerCase();
		password = HashProvider.md5(password);
		password = HashProvider.md5(password + salt);
		return password;
	}

	@Override
	public int getModeId() {
		return 3;
	}
}
