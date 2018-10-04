package com.gguri.swp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;
import com.gguri.swp.service.UserService;

@RequestMapping("/board")
@Controller
public class UserController {
	
	@Inject 
	private UserService service;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
		
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET() throws Exception{
		logger.info("login() GET>>>>");
		
	}
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, Model model) throws Exception{
		logger.info("login() POST>>>>");
		try {
			UserVO user = service.login(dto);
			if(user != null) { 
				model.addAttribute("user", user);
			} else {
				model.addAttribute("loginResult", "Login Fail!!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
