package com.gguri.swp.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO{
	@Inject
	private SqlSession session;
	
	private static String NS = "BoardMapper";
	private static String CREATE = NS + ".create";
	private static String READ = NS + ".read";
	private static String UPDATE = NS + ".update";
	private static String DELETE = NS + ".delete";
	private static String GETMAXBNO = NS + ".getMaxBno";
	private static String LISTPAGE = NS + ".listPage";
	private static String GETTOTALCOUNT = NS + ".gettotalcount";
	@Override
	public void create(BoardVO board) throws Exception {
		session.insert(CREATE, board);
		
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		return session.selectOne(READ,bno);
	}

	@Override
	public void update(BoardVO board) throws Exception {
		session.update(UPDATE, board);
		
	}

	@Override
	public void delete(Integer bno) throws Exception {
		session.delete(DELETE, bno);
		
	}

	@Override
	public Integer getMaxBno() throws Exception {
		return session.selectOne(GETMAXBNO);
	}
	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		return session.selectList(LISTPAGE,cri);
	}

	@Override
	public int getTotalCount(Criteria cri) throws Exception {
		return session.selectOne(GETTOTALCOUNT,cri);
	}

}
