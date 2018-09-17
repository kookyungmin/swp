package com.gguri.swp.service;

import com.gguri.swp.domain.MessageVO;

public interface MessageService {
	void addMessage(MessageVO message) throws Exception;
	MessageVO readMessage(String uid, Integer mno) throws Exception;
}
