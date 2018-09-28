package com.gguri.swp.persistence;

import java.util.List;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;

public interface BoardDAO {
	
	 void create(BoardVO board) throws Exception;
	 BoardVO read(Integer bno) throws Exception;
	 void update(BoardVO board) throws Exception;
	 void delete(Integer bno) throws Exception;
	 Integer getMaxBno() throws Exception;
	 List<BoardVO> listPage(Criteria cri) throws Exception;
	 int getTotalCount(Criteria cri) throws Exception;
	 void updateReplycnt(Integer bno, int amount) throws Exception;
	 void updateViewCnt(Integer bno) throws Exception;
	 void addAttach(String file) throws Exception;
}
