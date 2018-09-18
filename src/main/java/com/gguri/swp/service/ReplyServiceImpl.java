package com.gguri.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.ReplyVO;
import com.gguri.swp.persistence.BoardDAO;
import com.gguri.swp.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	private ReplyDAO replyDAO;
	
	@Inject
	private BoardDAO boardDAO;
	
	@Transactional
	@Override
	public void register(ReplyVO reply) throws Exception {
		replyDAO.create(reply);
		boardDAO.updateReplycnt(reply.getBno(), 1);
	}

	@Override
	public void modify(ReplyVO reply) throws Exception {
		replyDAO.update(reply);
		
	}
	
	@Transactional
	@Override
	public void remove(Integer rno) throws Exception {
		boardDAO.updateReplycnt(replyDAO.getBno(rno), -1);
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
	public ReplyVO read(Integer rno) throws Exception{
		return replyDAO.read(rno);
	}
	
}
