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
	 * @param password 密码
	 * @param email 登录标示
	 * @return String 返回加密的密码
	 */
	String encrypt(String password, String email);
	
	/**
	 * 登录时，检验密码
	 * @param password 密码
	 * @param email 登录标示
	 * @param hash 加密密码
	 * @return boolean 是否成功
	 * @throws PasswordInvalidException 密码不正确
	 */
	boolean validate(String password, String email, String hash) throws PasswordInvalidException;
}
