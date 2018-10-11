package com.gguri.swp.service;

import java.util.Date;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;

public interface UserService {
	public UserVO login(LoginDTO dto) throws Exception;
	public void keepLogin(String uid, String sessionId, Date expire) throws Exception;
	public UserVO checkLoginBefore(String value) throws Exception;
	public UserVO getBySns(UserVO snsUser) throws Exception;
}
