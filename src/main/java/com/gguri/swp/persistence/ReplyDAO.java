package com.gguri.swp.persistence;

import java.util.List;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.ReplyVO;

public interface ReplyDAO {
	
	void create(ReplyVO reply) throws Exception;
	
	void update(ReplyVO reply) throws Exception;
	
	void delete(Integer rno) throws Exception;
	
	void deleteAll(Integer bno) throws Exception;
	
	List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception;

	int getToalCount(Integer bno) throws Exception;

	ReplyVO read(Integer rno) throws Exception;
	
	int getBno(Integer rno) throws Exception;
}
