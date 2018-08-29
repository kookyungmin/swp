package com.gguri.swp.service;

import java.util.List;

import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.ReplyVO;


public interface ReplyService {
	List<ReplyVO> listReply(Integer bno) throws Exception;
	
	void register(ReplyVO vo) throws Exception;
	
	void modify(ReplyVO vo) throws Exception;
	
	void remove(Integer rno) throws Exception;
	
	List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception;
	
	int getTotalCount(Integer bno) throws Exception;
}
