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
     * @param uid
     * @return T
     * @throws UnauthorizedException
     */
    T verifyCookie(String uid) throws UnauthorizedException;

    /**
     * 验证用户登录
     * @param userName
     * @param password
     * @return T
     */
    T verifyUserPassword(String userName, String password) throws PasswordInvalidException;

    /**
     * 验证用户
     * @param user
     * @param password
     * @return T
     */
    T verifyUserPassword(T user, String password) throws PasswordInvalidException;

    /**
     * 验证用户是否有权访问
     * @param url
     */
    void verifyAccess(String url) throws PermissionDeniedException;
}
