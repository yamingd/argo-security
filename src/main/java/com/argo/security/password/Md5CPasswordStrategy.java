package com.argo.security.password;

import com.argo.security.HashProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("md5CPasswordStrategy")
public class Md5CPasswordStrategy extends AbstractPasswordStrategy {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String encrypt(String password, String email) {
		password = HashProvider.md5(password);
		String left = password.substring(0, 16);
		String right = password.substring(16);

		left = HashProvider.md5(left);
		right = HashProvider.md5(right);

		password = HashProvider.md5(left + right);

		return password;
	}

	@Override
	public int getModeId() {
		return 4;
	}
}
