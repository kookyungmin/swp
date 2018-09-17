package com.gguri.swp.persistence;

import com.gguri.swp.domain.MessageVO;

public interface MessageDAO {
	void create(MessageVO message) throws Exception;
	MessageVO readMessage(Integer mno) throws Exception;
	void updateState(Integer mno) throws Exception;
}
