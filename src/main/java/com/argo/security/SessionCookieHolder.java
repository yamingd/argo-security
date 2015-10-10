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
 * @date 2012-6-11
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
	 * @param request
	 * @return
	 * @throws UnauthorizedException
	 */
	public static String getCurrentUID(HttpServletRequest request)
			throws UnauthorizedException, CookieInvalidException, CookieExpiredException {

        String authCookieId = getAuthCookieId();

        return getCurrentUID(request, authCookieId);

	}

    public static String getCurrentUID(HttpServletRequest request, String authCookieId)
			throws UnauthorizedException, CookieInvalidException, CookieExpiredException {

        Cookie cookie = WebUtils.getCookie(request, authCookieId);
        String value = null;
        if (cookie == null) {
            value = request.getHeader(StringUtils.capitalize(authCookieId));
            if (StringUtils.isBlank(value)) {
                throw new UnauthorizedException("x-auth is NULL.");
            }
//            if (logger.isDebugEnabled()) {
//                logger.debug("Before {}:{}", authCookieId, value);
//            }
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
	 *
	 * @param response
	 * @param uid
	 */
    public static void setCurrentUID(HttpServletResponse response, String uid){
        String name = getAuthCookieId();
        setCurrentUID(response, uid, name);

    }

	/**
	 *
	 * @param response
	 * @param uid
	 * @param name
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
	 * @param response
	 */
    public static void removeCurrentUID(HttpServletResponse response){
        String name = getAuthCookieId();
        setCookie(response, name, "", 0);
    }

	/**
	 *
	 * @param response
	 * @param name
	 */
    public static void removeCurrentUID(HttpServletResponse response, String name){
        setCookie(response, name, "", 0);
    }
	/**
	 * 获取当前的SessionId
	 * @param request
	 * @param response
	 * @return
	 * @throws UnauthorizedException
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
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name){
		Cookie cookie = WebUtils.getCookie(request, name);
		return cookie;
	}
	
	/**
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value){
        String domain = getConfig().getDomain();
		String secure = getConfig().getSecure() ? "secure;" : "";
		String cvalue = String.format("%s=%s;Path=/;domain=%s;%sHTTPOnly", name, value,
                domain == null ? "" : domain, secure);
		response.addHeader("Set-Cookie",  cvalue);
	}
	
	/**
	 * @param response
	 * @param name
	 * @param value
	 * @param expireSeconds
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, Integer expireSeconds){
		String domain = getConfig().getDomain();
		String secure = getConfig().getSecure() ? "secure;" : "";
		String cvalue = String.format("%s=%s;Path=/;Max-Age=%s;domain=%s;%sHTTPOnly", name, value,
                expireSeconds, domain == null ? "" : domain,secure);
		response.addHeader("Set-Cookie",  cvalue);
	}
}
