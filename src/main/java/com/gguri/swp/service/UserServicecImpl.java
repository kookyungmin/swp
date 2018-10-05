package com.gguri.swp.service;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;
import com.gguri.swp.persistence.UserDAO;

@Service
public class UserServicecImpl implements UserService{
	
	@Inject
	private UserDAO userDAO;
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return userDAO.login(dto);
		
	}

	@Override
	public void keepLogin(String uid, String sessionId, Date expire) throws Exception{
		userDAO.keepLogin(uid, sessionId, expire);
	}

	@Override
	public UserVO checkLoginBefore(String loginCookie) throws Exception {
		return userDAO.checkLoginBefore(loginCookie); 
	}

}
