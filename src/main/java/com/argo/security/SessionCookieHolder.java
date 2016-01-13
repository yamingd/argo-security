package com.argo.security;

import com.argo.security.exception.CookieExpiredException;
import com.argo.security.exception.CookieInvalidException;
import com.argo.security.exception.UnauthorizedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 在Cookie中存储当前用户标识.
 *
 * @author yaming_deng
 */
public class SessionCookieHolder {

    private static Logger logger = LoggerFactory.getLogger(SessionCookieHolder.class);
	/**
	 * SESSION-ID
	 */
	public static final String KSESS = "x-sess";
	/**
	 * 验证COOKIE-ID
	 */
	public static final String KAUTH = "x-auth";

	public static CookieConfig getConfig(){
		return SecurityConfig.instance.getCookie();
	}
	
	public static String getAuthCookieId(){
		String temp = getConfig().getName();
		if(StringUtils.isBlank(temp)){
			return KAUTH;
		}
		return temp;
	}
	
	public static String getSessionCookieId(){
        String temp = getConfig().getSessionid();
		if(StringUtils.isBlank(temp)){
			return KSESS;
		}
		return temp;
	}
	
	/**
	 * 获取当前用户的标识，可以为id(Long)或email(String)
	 * @param request 当前请求
	 * @return String 用户标示
	 * @throws UnauthorizedException 不通过校验异常
	 * @throws CookieInvalidException Cookie数据不正确异常
	 * @throws CookieExpiredException Cookie过期异常
	 */
	public static String getCurrentUID(HttpServletRequest request)
			throws UnauthorizedException, CookieInvalidException, CookieExpiredException {

        String authCookieId = getAuthCookieId();

        return getCurrentUID(request, authCookieId);

	}

	/**
	 *
	 * @param request HTTP请求
	 * @param authCookieId Cookie名称
	 * @return String Cookie值
	 * @throws UnauthorizedException 不通过校验异常
	 * @throws CookieInvalidException Cookie数据不正确异常
	 * @throws CookieExpiredException Cookie过期异常
	 */
    public static String getCurrentUID(HttpServletRequest request, String authCookieId)
			throws UnauthorizedException, CookieInvalidException, CookieExpiredException {

        Cookie cookie = WebUtils.getCookie(request, authCookieId);
        String value = null;
        if (cookie == null) {
            value = request.getHeader(StringUtils.capitalize(authCookieId));
            if (StringUtils.isBlank(value)) {
                throw new UnauthorizedException("x-auth is NULL.");
            }
        } else {
            value = cookie.getValue();
        }
        String uid = CookieCipher.decrypt(authCookieId, value);
        if (uid == null) {
            throw new UnauthorizedException("x-auth is invalid");
        }
        return uid;
    }

	/**
	 * 保存请求用户Cookie
	 * @param response 请求响应
	 * @param uid 用户标示
	 */
    public static void setCurrentUID(HttpServletResponse response, String uid){
        String name = getAuthCookieId();
        setCurrentUID(response, uid, name);

    }

	/**
	 * 保存请求Cookie
	 * @param response 请求响应对象
	 * @param uid 用户标示
	 * @param name Cookie值
	 */
    public static void setCurrentUID(HttpServletResponse response, String uid, String name) {
        String value = null;
        try {
            value = CookieCipher.encrypt(name, uid);
            setCookie(response, name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 *
	 * @param response 请求响应对象
	 */
    public static void removeCurrentUID(HttpServletResponse response){
        String name = getAuthCookieId();
        setCookie(response, name, "", 0);
    }

	/**
	 *
	 * @param response 请求响应对象
	 * @param name 请求Cookie名称
	 */
    public static void removeCurrentUID(HttpServletResponse response, String name){
        setCookie(response, name, "", 0);
    }
	/**
	 * 获取当前的SessionId
	 * @param request 请求对象
	 * @param response 请求响应对象
	 * @return String 当前用户SessionId
	 */
	public static String getCurrentSessionID(HttpServletRequest request, HttpServletResponse response){
		Cookie cookie = WebUtils.getCookie(request, getSessionCookieId());
		if(cookie==null){
			String sess = HashProvider.md5(RandomStringUtils.randomAlphanumeric(10));
			cookie = new Cookie(getSessionCookieId(), sess);
			cookie.setPath("/");
			response.addCookie(cookie);
			return sess;
		}else{
			return cookie.getValue();
		}
	}
	
	/**
	 * @param request 请求对象
	 * @param name Cookie名称
	 * @return Cookie Cookie实例
	 */
	public static Cookie getCookie(HttpServletRequest request, String name){
		Cookie cookie = WebUtils.getCookie(request, name);
		return cookie;
	}
	
	/**
	 * @param response 请求响应对象
	 * @param name Cookie名称
	 * @param value Cookie值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value){
        String domain = getConfig().getDomain();
		String secure = getConfig().getSecure() ? "secure;" : "";
		String cvalue = String.format("%s=%s;Path=/;domain=%s;%sHTTPOnly", name, value,
                domain == null ? "" : domain, secure);
		response.addHeader("Set-Cookie",  cvalue);
	}
	
	/**
	 * @param response 请求响应对象
	 * @param name Cookie名称
	 * @param value Cookie值
	 * @param expireSeconds Cookie过期时间(秒)
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, Integer expireSeconds){
		String domain = getConfig().getDomain();
		String secure = getConfig().getSecure() ? "secure;" : "";
		String cvalue = String.format("%s=%s;Path=/;Max-Age=%s;domain=%s;%sHTTPOnly", name, value,
                expireSeconds, domain == null ? "" : domain,secure);
		response.addHeader("Set-Cookie",  cvalue);
	}
}
