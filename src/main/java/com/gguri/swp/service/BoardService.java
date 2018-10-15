package com.gguri.swp.service;

import java.util.List;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.UserVO;

public interface BoardService {
	void regist(BoardVO board) throws Exception;
	BoardVO read(Integer bno) throws Exception;
	void modify(BoardVO board) throws Exception;
	void remove(Integer bno) throws Exception;
	void dummy() throws Exception;
	List<BoardVO> listPage(Criteria cri) throws Exception;
	int getTotalCount(Criteria cri) throws Exception;
	List<String> getAttach(Integer bno) throws Exception;
	void deleteAttach(String fileName) throws Exception;
	void appendAttach(String[] fullNames, Integer bno) throws Exception;
	String getTime();
	String getUname(String uid);
	UserVO getUser(String uid);
}
