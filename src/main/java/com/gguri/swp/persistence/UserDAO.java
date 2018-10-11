package com.gguri.swp.persistence;



import java.util.Date;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;

public interface UserDAO {
	public UserVO login(LoginDTO dto) throws Exception;
	public void keepLogin(String uid, String sessionId, Date expire) throws Exception;
	public UserVO checkLoginBefore(String loginCookie) throws Exception;
	public UserVO getBySns(UserVO snsUser) throws Exception;
}
