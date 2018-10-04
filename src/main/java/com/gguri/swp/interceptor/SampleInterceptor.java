package com.gguri.swp.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gguri.swp.domain.BoardVO;

public class SampleInterceptor extends HandlerInterceptorAdapter{
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
		logger.debug("postHandle>>>>>> {}", request.getRequestURI());
		List<BoardVO> list = (List)modelAndView.getModel().get("list");
		logger.debug("postHandle>>>>>> {}", list.size());
	}

	
}
