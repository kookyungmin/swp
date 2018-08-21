package com.gguri.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;
import com.gguri.swp.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{
	@Inject
	private BoardDAO boardDAO;
	
	@Override
	public void regist(BoardVO board) throws Exception {
		boardDAO.create(board);
		
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		return boardDAO.read(bno);
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		boardDAO.update(board);
		
	}

	@Override
	public void remove(Integer bno) throws Exception {
		boardDAO.delete(bno);
		
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return boardDAO.listAll();
	}
	
	//dummy 추가
	@Override
	public void dummy() throws Exception {
		int maxBno;
		if(boardDAO.getMaxBno() == null)
		{
			maxBno = 0;
		}else {
			maxBno = boardDAO.getMaxBno();
		}
		BoardVO board = new BoardVO();
		
		for(int i = maxBno + 1; i < maxBno + 101; i++ ) {
			board.setBno(i);
			board.setTitle("dummytitle"+i);
			board.setContent("연습용 게시물입니다!");
			board.setWriter("꾸리");
			regist(board);
		}
		
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		return boardDAO.listPage(page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return boardDAO.listCriteria(cri);
	}

}
