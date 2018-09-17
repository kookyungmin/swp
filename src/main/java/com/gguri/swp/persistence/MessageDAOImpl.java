package com.gguri.swp.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gguri.swp.domain.MessageVO;

@Repository
public class MessageDAOImpl implements MessageDAO{
	@Inject
	private SqlSession session;
	
	private static final String NS = "MessageMapper";
	private static final String CREATE_MESSAGE = NS + ".create_message";
	private static final String READ_MESSAGE = NS + ".read_message";
	private static final String UPDATE_MESSAGE = NS + ".update_message";
	
	@Override
	public void create(MessageVO message) throws Exception {
		session.insert(CREATE_MESSAGE, message);
		
	}

	@Override
	public MessageVO readMessage(Integer mno) throws Exception {
		return session.selectOne(READ_MESSAGE, mno);
		
	}

	@Override
	public void updateState(Integer mno) throws Exception {
		session.update(UPDATE_MESSAGE, mno);
	}
	
}
