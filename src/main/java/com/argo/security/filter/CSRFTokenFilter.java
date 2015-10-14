package com.argo.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CSRF攻击检查
 *
 * @author yaming_deng
 */
public class CSRFTokenFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1179085738490906926L;
	
	private FilterConfig config = null;
	private String[] filterUrls = null;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
				
	}

	protected boolean isAjax(HttpServletRequest request){
    	return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String method = httpRequest.getMethod();
		if("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)){
			if(!this.isUrlFiltered(httpRequest)){
				String formToken = httpRequest.getParameter("_csrf_");
                if (StringUtils.isBlank(formToken)){
                    formToken = httpRequest.getHeader("X-CSRFToken");
                }
				Cookie clientToken = WebUtils.getCookie(httpRequest, "_csrf_");
				if(clientToken==null 
						|| StringUtils.isBlank(formToken) 
						|| !formToken.equals(clientToken.getValue())){
					
					if(this.isAjax(httpRequest)){
						HttpServletResponse resp = (HttpServletResponse)response;
						resp.setStatus(401);
						resp.getWriter().write("{'error':'You are not allowed to execute this action'}");
						resp.setContentType("application/json; charset=UTF-8");
						resp.addHeader("Cache-Control", "no-store");
						resp.addHeader("Pragma", "no-cache");
						resp.getWriter().flush();
						resp.getWriter().close();
						return;
					}else{
						throw new ServletException("You are not allowed to execute this action. _csrf_ checking");
					}
				}
			}
		}
		
		chain.doFilter(request, response);
	}
	
	/**
	 * URL是否需要过滤掉.
	 * @param request
	 * @return boolean
	 */
	private boolean isUrlFiltered(HttpServletRequest request){
		if(this.filterUrls==null){
			return false;
		}
        String url = request.getRequestURI();
        url = url.replace(request.getContextPath(), "");
		url = url.trim().toLowerCase();
		for(String item : this.filterUrls){
			if(url.startsWith(item.toLowerCase())){
				return true;
			}
		} 
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;	
		if(this.config!=null){
			String value = this.config.getInitParameter("filters");
			filterUrls = StringUtils.split(value, ",");
		}
	}
}
