package com.gguri.swp.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.service.UserService;

public class AuthInterceptor extends HandlerInterceptorAdapter implements SessionNames{
	private static final Logger logger = LoggerFactory.getLogger(SampleInterceptor.class);
	
	@Inject
	private UserService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//세션 브라우저마다 서버에 저장됨
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) == null) {
			Cookie loginCookie = WebUtils.getCookie(request, SessionNames.LOGIN_COOKIE);
			if (loginCookie != null) {
				UserVO loginedUser = service.checkLoginBefore(loginCookie.getValue());
				if(loginedUser != null) {
					session.setAttribute(LOGIN, loginedUser);
					return true;
				}
			}
			
			String uri = request.getRequestURI();
			String httpMethod = request.getMethod();
			if(StringUtils.contains(uri, "/replies/") && StringUtils.equalsIgnoreCase(httpMethod, "GET")) {
				response.sendError(401, "Need Login");
				return false;
			}
			saveAttemptedLocation(request, session);
			
			response.sendRedirect("/login");
		}
		
		return true;
	}


	private void saveAttemptedLocation(HttpServletRequest request, HttpSession session) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		if (StringUtils.isNotEmpty(query))
			uri += "?" + query;
		
		session.setAttribute(ATTEMPTED, uri);
	}
}
