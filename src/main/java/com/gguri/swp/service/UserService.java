package com.gguri.swp.service;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;

public interface UserService {
	public UserVO login(LoginDTO dto) throws Exception;
}
