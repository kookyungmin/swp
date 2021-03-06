package com.gguri.swp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SampleInterceptor extends HandlerInterceptorAdapter implements SessionNames{
	private static final Logger logger = LoggerFactory.getLogger(SampleInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("preHandle>>>>>> {}", request.getRequestURI());
		logger.debug("preHandle>>>>>> {}", handler);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView)
			throws Exception {
		
		HandlerMethod method = (HandlerMethod) handler;
		HttpSession session = request.getSession();
		
		Cookie loginCookie = new Cookie(LOGIN_COOKIE, session.getId());
	}

	
}
