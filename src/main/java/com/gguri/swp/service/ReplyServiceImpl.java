package com.gguri.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.ReplyVO;
import com.gguri.swp.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Inject
	ReplyDAO replyDAO;
	
	@Override
	public void register(ReplyVO reply) throws Exception {
		replyDAO.create(reply);
		
	}

	@Override
	public void modify(ReplyVO reply) throws Exception {
		replyDAO.update(reply);
		
	}

	@Override
	public void remove(Integer rno) throws Exception {
		replyDAO.delete(rno);
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		return replyDAO.listPage(bno, cri);
	}

	@Override
	public int getTotalCount(Integer bno) throws Exception {
		return replyDAO.getToalCount(bno);
	}

	@Override
	public BoardVO read(Integer rno) {
		return replyDAO.read(rno);
	}
	
}
