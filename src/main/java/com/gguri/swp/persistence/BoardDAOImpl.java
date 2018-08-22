package com.gguri.swp.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO{
	/*SQL문을 실행하는 메소드를 가지고 있는 인터페이스 SqlSession을 구현한 SqlSessionTemplate을 찾아서
	   객체를 자동으로 생성*/
	@Inject
	private SqlSession session;
	
	private static String NS = "BoardMapper";
	private static String CREATE = NS + ".create";
	private static String READ = NS + ".read";
	private static String UPDATE = NS + ".update";
	private static String DELETE = NS + ".delete";
	private static String LISTALL = NS + ".listAll";
	private static String GETMAXBNO = NS + ".getMaxBno";
	private static String LISTPAGE = NS + ".listPage";
	private static String LISTCRITERIA = NS + ".listCriteria";
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
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(LISTALL);
	}

	@Override
	public Integer getMaxBno() throws Exception {
		return session.selectOne(GETMAXBNO);
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		if (page <= 0) {
			page = 1;
		}
		
		page = (page - 1)*10;
		
		return session.selectList(LISTPAGE, page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return session.selectList(LISTCRITERIA,cri);
	}

	@Override
	public int getTotalCount() throws Exception {
		return session.selectOne(GETTOTALCOUNT);
	}

}
