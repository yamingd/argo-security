package com.argo.security.password;

import com.argo.security.exception.PasswordInvalidException;

/**
 * 用来兼容各个集成系统的加密方式.
 * @author yaming_deng
 *
 */
public interface PasswordStrategy {

	int getModeId();
	/**
	 * 生成加密密码，存放到数据库.
	 * @param password
	 * @param email
	 * @return String
	 */
	String encrypt(String password, String email);
	
	/**
	 * 登录时，检验密码
	 * @param password
	 * @param email
	 * @param hash
	 * @return boolean
	 */
	boolean validate(String password, String email, String hash) throws PasswordInvalidException;
}
