package com.argo.security.service;

import com.argo.security.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权和鉴权认证服务.
 * Created with IntelliJ IDEA.
 * User: yamingdeng
 * Date: 13-11-17
 * Time: 上午10:02
 */
public interface AuthorizationService<T> {

    /**
     * 验证Cookie的UserId
     * @param uid
     * @return
     * @throws UnauthorizedException
     */
    T verifyCookie(HttpServletRequest request, HttpServletResponse response, String uid)
            throws CookieInvalidException, CookieExpiredException;

    /**
     * 验证用户登录
     * @param userName
     * @param password
     * @return
     */
    T verifyUserPassword(String userName, String password) throws PasswordInvalidException;

    /**
     * 验证用户
     * @param user
     * @param password
     * @return
     */
    T verifyUserPassword(T user, String password) throws PasswordInvalidException;

    /**
     * 验证用户是否有权访问
     * @param url
     * @return
     */
    void verifyAccess(String url) throws PermissionDeniedException;
}
