package com.gguri.swp.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gguri.swp.domain.MessageVO;
import com.gguri.swp.persistence.MessageDAO;
import com.gguri.swp.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Inject
	MessageDAO messageDao;
	
	@Inject
	PointDAO pointDao;
	
	private static final int READ_POINT = 5;
	
	@Override
	public void addMessage(MessageVO message) throws Exception {
		messageDao.create(message);
	}

	@Override
	public MessageVO readMessage(String uid, Integer mno) throws Exception {
		messageDao.updateState(mno); //메시지 opendate 를 현재 시간으로 바꿔줌
		pointDao.updatePoint(uid, READ_POINT); //user의 point를 5증가
		return messageDao.readMessage(mno);
	}

}
