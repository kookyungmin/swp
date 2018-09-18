package com.gguri.swp;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gguri.swp.domain.MessageVO;
import com.gguri.swp.service.MessageService;

//Runner 클래스(테스트 메소드를 실행하는 클래스) 를 SpringJUnit4ClassRunner로 함
@RunWith(SpringJUnit4ClassRunner.class)
//location 속성 경로에 있는 xml 파일을 이용해서 스프링이 로딩됨
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class MessageServiceTest {
	@Inject
	private MessageService service;
	private static Logger logger = LoggerFactory.getLogger(MessageServiceTest.class);
	
	@Test
	public void testWriteMessage() throws Exception{
		MessageVO msg = new MessageVO();
		msg.setSender("user1");
		msg.setTargetid("user2");
		msg.setMessage("Messgae 내용2");
		logger.info("MESSAGE>>" + msg);
		service.addMessage(msg);
	}
	
	@Ignore
	public void testReadMessage() throws Exception{
		MessageVO msg = service.readMessage("user1", 1);
		logger.info("READ >>"+ msg);
	}
}
