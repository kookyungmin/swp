package com.gguri.swp.persistence;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;

public interface UserDAO {
	public UserVO login(LoginDTO dto) throws Exception;
}
