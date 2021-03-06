package com.gguri.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.gguri.mapper.SampleMapper;
import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.UserVO;
import com.gguri.swp.persistence.BoardDAO;
import com.gguri.swp.persistence.ReplyDAO;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Inject
	private BoardDAO boardDAO;
	@Inject
	private ReplyDAO replyDAO;
	@Inject
	private SampleMapper samplemapper;
	
	@Transactional
	@Override
	public void regist(BoardVO board) throws Exception {
		//게시물 삽입
		boardDAO.create(board);
		String[] files = board.getFiles();
		if(files == null) return;
		
		for(String file : files) {
			boardDAO.addAttach(file);
		}
	}
	
	//대부분의 데이터베이스가 기본으로 사용하는 수준으로, 다른 연결이 커밋하지 않은 데이터는 볼 수 없도록 함
	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public BoardVO read(Integer bno) throws Exception {
		boardDAO.updateViewCnt(bno);
		return boardDAO.read(bno);
	}
	@Override
	public void modify(BoardVO board) throws Exception {
		boardDAO.update(board);
		
	}
	
	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		boardDAO.deleteAllAttach(bno);
		replyDAO.deleteAll(bno);
		boardDAO.delete(bno);
		
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
			board.setTitle("dummytitle"+i);
			board.setContent("연습용 게시물입니다!");
			board.setWriter("꾸리");
			regist(board);
		}
	}
	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		return boardDAO.listPage(cri);
	}
	@Override
	public int getTotalCount(Criteria cri) throws Exception {
		return boardDAO.getTotalCount(cri);
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		return boardDAO.getAttach(bno);
	}

	@Override
	public void deleteAttach(String fileName) throws Exception {
		boardDAO.deleteAttach(fileName);
		
	}
	
	@Transactional
	@Override
	public void appendAttach(String[] fullNames, Integer bno) throws Exception {
		for(String fullName : fullNames)
			boardDAO.appendAttach(fullName, bno);
		
	}

	@Override
	public String getTime() throws Exception{
		return samplemapper.getTime();
	}

	@Override
	public String getUname(String uid) throws Exception{
		return samplemapper.getUname(uid);
	}

	@Override
	public UserVO getUser(String uid) throws Exception{
		return samplemapper.getUser(uid);
	}
	
}
