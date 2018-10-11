package com.gguri.swp.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	private static final String NS = "LoginMapper";
	private static final String LOGIN = NS + ".login";
	private static final String KEEPLOGIN = NS + ".keepLogin";
	private static final String CHECKLOGINBEFORE = NS + ".checkUserWithSessionKey";
	private static final String GETBYSNSGOOGLE = NS + ".getBySnsGoogle";
	private static final String GETBYSNSNAVER = NS + ".getBySnsNaver";
	
	@Inject
	private SqlSession session;
	
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return session.selectOne(LOGIN, dto);
	}


	@Override
	public void keepLogin(String uid, String sessionId, Date expire) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("sessionId", sessionId);
		map.put("expire", expire);
		session.update(KEEPLOGIN, map);
	}


	@Override
	public UserVO checkLoginBefore(String loginCookie) throws Exception {
		return session.selectOne(CHECKLOGINBEFORE, loginCookie);
		
	}


	@Override
	public UserVO getBySns(UserVO snsUser) throws Exception {
		if(StringUtils.isNotEmpty(snsUser.getNaverId())) {
			return session.selectOne(GETBYSNSNAVER, snsUser);
		}else {
			return session.selectOne(GETBYSNSGOOGLE, snsUser);
		}
		
	}
}
