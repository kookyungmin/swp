package com.gguri.swp.persistence;

import java.util.List;

import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.ReplyVO;

public interface ReplyDAO {
	List<ReplyVO> list(Integer bno) throws Exception;
	
	void create(ReplyVO vo) throws Exception;
	
	void update(ReplyVO vo) throws Exception;
	
	void delete(Integer rno) throws Exception;
	
	List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception;

	int getToalCount(Integer bno);
}
