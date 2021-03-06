package com.gguri.swp.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO{
	
	@Inject
	private SqlSession session;
	
	private static final String NS = "PointMapper";
	private static final String UPDATE_POINT = NS + ".update_point";
	
	@Override
	public void updatePoint(String uid, int upoint) throws Exception {
		Map<String, Object>  paramMap = new HashMap<>();
		paramMap.put("uid", uid);
		paramMap.put("upoint", upoint);
		session.update(UPDATE_POINT, paramMap);
		
	}

}
