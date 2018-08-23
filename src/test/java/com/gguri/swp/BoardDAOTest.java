package com.gguri.swp;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.persistence.BoardDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class BoardDAOTest {
	@Inject
	private BoardDAO boardDAO;
	
	private static final Logger logger =
		LoggerFactory.getLogger(BoardDAOTest.class);
	private static boolean didupdate = false;
	private static int maxbno = 0;
	
	
	@Before
	public void getMaxBno() throws Exception{
		if(maxbno == 0) {
			boardDAO.create(createBoard("새로운 글을 넣음","새로운 글을 넣음"));
			maxbno=boardDAO.getMaxBno();
		}
	}
	
	@Test
	public void readTest() throws Exception {
		logger.info(boardDAO.read(maxbno).toString());
	}
	
	@Test
	public void updateTest() throws Exception{
		BoardVO board = createBoard("글이 수정됨","수정테스트");
		board.setBno(maxbno);
		boardDAO.update(board);
		didupdate = true;
	}
	
	@After
	public void deleteTest() throws Exception{
		if (didupdate  == true) {
			logger.info(boardDAO.listAll().toString());
			boardDAO.delete(maxbno);
			didupdate = false;
		}
	}
	
	private BoardVO createBoard(String title, String content) {
		BoardVO board = new BoardVO();
		board.setTitle(title);
		board.setContent(content);
		board.setWriter("user00");
		return board;
	}
	
	@Test
	public void testURI() throws Exception{
		int bno = 1;
		int perPageNum = 20;
//		UriComponents uriComponets = UriComponentsBuilder.newInstance()
//				.path("/board/read")
//				.queryParam("bno", bno)
//				.queryParam("perPageNum", perPageNum)
//				.build();
		UriComponents uriComponets = UriComponentsBuilder.newInstance()
		.path("/{module}/{page}")
		.queryParam("bno", bno)
		.queryParam("perPageNum", perPageNum)
		.build()
		.expand("board","read")
		.encode();
		
		
		String uri = "/board/read?bno=" + bno + "&perPageNum=" + perPageNum;
		logger.info(uri);
		logger.info(uriComponets.toString());
		
		assertEquals(uri, uriComponets.toString());
	}
}
