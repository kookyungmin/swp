package com.gguri.swp;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.persistence.BoardDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class BoardDAOTest {
	@Inject
	private BoardDAO boardDAO;
	
	private static final Logger logger =
		LoggerFactory.getLogger(BoardDAOTest.class);
	private static boolean flag = false;
	private static int cnt = 0;
	
	@Before
	public void selectTest() throws Exception {
		if (flag == false) {
			boardDAO.create(createBoard("새로운 글을 넣음","새로운 글을 넣음"));
			flag = true;
		}
	}
	
	@Test
	public void readTest() throws Exception {
		logger.info(boardDAO.read().toString());
		cnt++;
	}
	
	@Test
	public void updateTest() throws Exception{
		boardDAO.update(createBoard("수정된 글임","수정 테스트"));
		cnt++;
	}
	
	@After
	public void deleteTest() throws Exception{
		if(cnt == 2) {
			logger.info(boardDAO.listAll().toString());
			boardDAO.delete();
		}
	}
	
	private BoardVO createBoard(String title, String content) {
		BoardVO board = new BoardVO();
		board.setTitle(title);
		board.setContent(content);
		board.setWriter("user00");
		return board;
	}
}
