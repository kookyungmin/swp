package com.gguri.swp.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.gguri.swp.domain.UserVO;
import com.gguri.swp.interceptor.SessionNames;

public class ReplyEchoHandler extends TextWebSocketHandler{
	List<WebSocketSession> sessions = new ArrayList<>();
	Map<String, WebSocketSession> userSessions = new HashMap<>();
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished:" + session);
		sessions.add(session);
		String senderId = getId(session);
		userSessions.put(senderId, session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage:" + session + ", " + message);
		String senderId = getId(session);
		//모든 유저에게 메시지 보내기
//		for(WebSocketSession sess: sessions) {
//			sess.sendMessage(new TextMessage(senderId + ":" + message.getPayload()));
//		}
		
		//protocol : cmd, 댓글작성자, 게시글작성자, bno (reply, user2, user1, 6)
		String msg = message.getPayload();
		if(StringUtils.isNotEmpty(msg)) {
			String[] strs = msg.split(",");
			if(strs != null && strs.length == 4) {
				String cmd = strs[0];
				String replyWriter = strs[1];
				String boardWriter = strs[2];
				String bno = strs[3];
				
				WebSocketSession boardWriterSession = userSessions.get(boardWriter);
				if("reply".equals(cmd) && boardWriterSession != null) {
					TextMessage tmp = new TextMessage(replyWriter + "님이" + bno + "번 게시물에 댓글을 달았습니다.");
					boardWriterSession.sendMessage(tmp);
				}
			}
		}
	}

	private String getId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		UserVO loginUser = (UserVO)httpSession.get(SessionNames.LOGIN);
		
		if(null == loginUser) {
			return session.getId();
		}else {
			return loginUser.getUid();
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed:" + session + ", " + status);
	}
}
