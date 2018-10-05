package com.gguri.swp.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;
import com.gguri.swp.interceptor.SessionNames;
import com.gguri.swp.service.UserService;

@Controller
public class UserController {
	
	@Inject 
	private UserService service;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, 
			           HttpServletRequest request,
			           HttpServletResponse response) throws Exception{
		logger.info("logout() GET>>>>");
		session.removeAttribute(SessionNames.LOGIN);
		//세션 전체를 비움
		session.invalidate();
		Cookie loginCookie = WebUtils.getCookie(request, SessionNames.LOGIN);
		if(loginCookie != null) {
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
			
			UserVO user = (UserVO)session.getAttribute(SessionNames.LOGIN);
			service.keepLogin(user.getUid(), session.getId(), new Date());
		}
		return "/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET() throws Exception{
		logger.info("login() GET>>>>");
		
	}
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, Model model,
						  HttpSession session) throws Exception{
		logger.info("login() POST>>>>");
		try {
			UserVO user = service.login(dto);
			if(user != null) { 
				Date expire = new Date(System.currentTimeMillis() + SessionNames.EXPIRE * 1000);
				service.keepLogin(user.getUid(), session.getId(), expire);
				model.addAttribute("user", user);
			} else {
				model.addAttribute("loginResult", "Login Fail!!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
}
