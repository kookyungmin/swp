package com.gguri.swp.service;

import java.util.List;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;

public interface BoardService {
	void regist(BoardVO board) throws Exception;
	BoardVO read(Integer bno) throws Exception;
	void modify(BoardVO board) throws Exception;
	void remove(Integer bno) throws Exception;
	void dummy() throws Exception;
	List<BoardVO> listPage(Criteria cri) throws Exception;
	int getTotalCount(Criteria cri) throws Exception;
}
