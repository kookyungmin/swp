package com.gguri.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.ReplyVO;
import com.gguri.swp.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Inject
	ReplyDAO replyDAO;
	
	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		return replyDAO.list(bno);
	}

	@Override
	public void register(ReplyVO vo) throws Exception {
		replyDAO.create(vo);
		
	}

	@Override
	public void modify(ReplyVO vo) throws Exception {
		replyDAO.update(vo);
		
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
	
}
