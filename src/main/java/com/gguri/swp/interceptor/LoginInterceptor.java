package com.gguri.swp.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gguri.mapper.SampleMapper;
import com.gguri.swp.domain.UserVO;

public class LoginInterceptor extends HandlerInterceptorAdapter implements SessionNames{
	private static final Logger logger = LoggerFactory.getLogger(SampleInterceptor.class);
	@Inject
	private SampleMapper sampleMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//세션 브라우저마다 서버에 저장됨
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null) {
			session.removeAttribute(LOGIN);
		}
		
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView)
			throws Exception {
		
		HttpSession session = request.getSession();
		Object user = modelAndView.getModelMap().get("user");
		logger.debug("postHandle>>>>>>>>>>>>{}" , user);
		if (user != null) {
			session.setAttribute(LOGIN, user);
			UserVO userVO = (UserVO)session.getAttribute(SessionNames.LOGIN);
			System.out.println(request.getRemoteAddr());
			sampleMapper.setLoginIp(request.getRemoteAddr(), userVO.getUid());
			sampleMapper.setLastLogion(userVO.getUid());
			
			if(StringUtils.isNotEmpty(request.getParameter("useCookie"))) {
				Cookie loginCookie = new Cookie(LOGIN_COOKIE, session.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(7 * 24 * 60 * 60);
				response.addCookie(loginCookie);
			}
			String attempted = (String)session.getAttribute(ATTEMPTED);
			if (StringUtils.isNotEmpty(attempted)) {
				response.sendRedirect(attempted);
				session.removeAttribute(ATTEMPTED);
			}
			response.sendRedirect("/board/listPage");
		}
	}
}
