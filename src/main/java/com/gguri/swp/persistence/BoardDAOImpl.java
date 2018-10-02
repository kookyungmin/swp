package com.gguri.swp.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static String UPDATEREPLYCNT = NS + ".updateReplyCnt";
	private static String UPDATEVIEWCNT = NS + ".updateViewCnt";
	private static String ADDATTACH = NS + ".addAttach";
	private static String GETATTACH = NS + ".getAttach";
	private static String DELETEATTACH = NS + ".deleteAttach";
	private static String APPENDATTACH = NS + ".appendAttach";
	private static String DELETEALLATTACH = NS + ".deleteAllAttach";
	
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

	@Override
	public void updateReplycnt(Integer bno, int amount) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount",amount);
		session.update(UPDATEREPLYCNT, map);
	}

	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		session.update(UPDATEVIEWCNT, bno);
		
	}

	@Override
	public void addAttach(String fullName) throws Exception {
		session.insert(ADDATTACH, fullName);
		
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		return session.selectList(GETATTACH, bno);
	}

	@Override
	public void deleteAttach(String fileName) throws Exception {
		session.delete(DELETEATTACH, fileName);
		
	}

	@Override
	public void appendAttach(String fullName, Integer bno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("fullName", fullName);
		map.put("bno", bno);
		session.insert(APPENDATTACH, map);
		
	}

	@Override
	public void deleteAllAttach(Integer bno) throws Exception {
		session.delete(DELETEALLATTACH, bno);
		
	}

}
