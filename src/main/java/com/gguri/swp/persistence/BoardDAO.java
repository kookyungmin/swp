package com.gguri.swp.persistence;

import java.util.List;

import com.gguri.swp.domain.BoardVO;

public interface BoardDAO {
	
	public void create(BoardVO board) throws Exception;
	
	public BoardVO read() throws Exception;
	
	public void update(BoardVO board) throws Exception;
	
	public void delete() throws Exception;
	
	public List<BoardVO> listAll() throws Exception;
	
}
