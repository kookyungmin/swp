package com.gguri.swp.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.dto.LoginDTO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	private static final String NS = "LoginMapper";
	private static final String LOGIN = NS + ".login";
	
	@Inject
	private SqlSession session;
	
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return session.selectOne(LOGIN, dto);
	}

}
