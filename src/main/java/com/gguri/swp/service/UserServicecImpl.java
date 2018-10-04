package com.gguri.swp.service;

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

}
