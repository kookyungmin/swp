package com.gguri.swp.persistence;

import java.util.List;

import com.gguri.swp.domain.BoardVO;

public interface BoardDAO {
	
	public void create(BoardVO board) throws Exception;
	
	public BoardVO read(Integer bno) throws Exception;
	
	public void update(BoardVO board) throws Exception;
	
	public void delete(Integer bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
	public Integer getMaxBno() throws Exception;
}
