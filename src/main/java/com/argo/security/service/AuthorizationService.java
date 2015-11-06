package com.argo.security.service;

import com.argo.security.UserIdentity;
import com.argo.security.exception.*;

/**
 * 授权和鉴权认证服务.
 * User: yamingdeng
 */
public interface AuthorizationService<T extends UserIdentity> {
    /**
     * 验证Cookie的UserId
     * @param uid 用户标示
     * @return T 返回用户实例
     * @throws UnauthorizedException 用户校验不通过
     */
    T verifyCookie(String uid) throws UnauthorizedException;

    /**
     * 验证用户登录
     * @param userName 用户登录名
     * @param password 用户密码
     * @return T 用户实例
     * @throws PasswordInvalidException 密码不正确异常
     */
    T verifyUserPassword(String userName, String password) throws PasswordInvalidException;

    /**
     * 验证用户
     * @param user 用户实例
     * @param password 密码
     * @return T 通过验证的用户实例
     * @throws PasswordInvalidException 密码不正确异常
     */
    T verifyUserPassword(T user, String password) throws PasswordInvalidException;

    /**
     * 验证用户是否有权访问
     * @param url 要访问的URL
     * @throws PermissionDeniedException 拒接访问异常
     */
    void verifyAccess(String url) throws PermissionDeniedException;
}
