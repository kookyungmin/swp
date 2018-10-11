package com.gguri.swp.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.gguri.swp.auth.SnsLogin;
import com.gguri.swp.auth.SnsValue;
import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;
import com.gguri.swp.interceptor.SessionNames;
import com.gguri.swp.service.UserService;

@Controller
public class UserController {
	
	@Inject 
	private UserService service;
	
	@Inject
	private SnsValue naverSns;
	
	@Inject 
	private SnsValue googleSns;
	
	@Inject
	private GoogleConnectionFactory googleConnectionFactory;
	
	@Inject
	private OAuth2Parameters googleOAuth2Parameters;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/auth/{service}/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String snsLoginCallback(Model model, 
								   @RequestParam String code,
								   @PathVariable String snsService,
								   HttpSession session) throws Exception {
		logger.debug("loginCallback>>>>>> service={}",snsService);
		SnsValue sns = null;
		if(StringUtils.equals("naver", snsService)) {
			sns = naverSns;
		}else {
			sns = googleSns;
		}
		
		//1. code를 이용해서 access_token 받기
		//2. access_token으로 profile 정보가져아기
		SnsLogin snsLogin = new SnsLogin(sns);
		UserVO snsUser = snsLogin.getUserProfile(code);
		logger.debug("loginCallback>>>>> profile= {}" ,snsUser);
		model.addAttribute("result", snsUser);
		//3. DB에 저장
		
		UserVO user = service.getBySns(snsUser);
		
		if(user == null) {
			model.addAttribute("result", "존재하지 않는 사용자입니다. 가입해주세요");
		} else {
			model.addAttribute("result", user.getUname() + "님 반값습니다.");
			//4. 존재시 강제로그인 미존재시 가입화면
			session.setAttribute(SessionNames.LOGIN, user);
		}
		return "loginResult";
	}
	
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
	public void loginGET(Model model) throws Exception{
		logger.info("login() GET>>>>");
		
		//네이버 auth url 가져옴 (직접 구현)
		SnsLogin snsLogin = new SnsLogin(naverSns);
		model.addAttribute("naver_url", snsLogin.getNaverAuthURL());
		
		//구글 auth url 가져옴 (이미 구현됨)
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		model.addAttribute("google_url", url);
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
