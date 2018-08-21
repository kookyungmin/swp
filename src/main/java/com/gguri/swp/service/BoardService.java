package com.gguri.swp.service;

import java.util.List;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;

public interface BoardService {
	void regist(BoardVO board) throws Exception;
	BoardVO read(Integer bno) throws Exception;
	void modify(BoardVO board) throws Exception;
	void remove(Integer bno) throws Exception;
	List<BoardVO> listAll() throws Exception;
	//dummy 추가
	void dummy() throws Exception;
	//listPage
	List<BoardVO> listPage(int page) throws Exception;
	//listCriteria
	List<BoardVO> listCriteria(Criteria cri) throws Exception;
}
