package com.argo.security.password;

import com.argo.security.SecurityConfig;
import com.argo.security.exception.PasswordInvalidException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractPasswordStrategy implements PasswordStrategy, InitializingBean {
	
	@Autowired
	@Qualifier("passwordStrategyFactory")
	private PasswordStrategyFactory factory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SecurityConfig.load();
		this.factory.add(this);
	}

	@Override
	public boolean validate(String password, String email, String hash) throws PasswordInvalidException {
		String encrypt_passwd = this.encrypt(password, email);
		boolean valid =  hash.equals(encrypt_passwd);
		if (!valid){
			throw new PasswordInvalidException();
		}
		return true;
	}
}
