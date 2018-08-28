package com.gguri.swp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/*.xml")
public class BoardControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(BoardControllerTest.class);
	
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mockmvc;
	
	@Before
	public void setup() {
		//스프링이 준 WebApplicationContext를 이용해서 mockmvc를 생성
		this.mockmvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		logger.debug("setup BoardControllerTest mockMvc...");
	}
	
	@Ignore
	public void testListPage() throws Exception {
		this.mockmvc.perform(get("/board/listPage").param("page","2"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(handler().handlerType(BoardController.class))
		.andExpect(handler().methodName("listPage"));
	}
	
	@Ignore
	public void testRead() throws Exception{
		this.mockmvc.perform(get("/board/read").param("bno", "2"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(handler().handlerType(BoardController.class))
		.andExpect(handler().methodName("read"));
	}
	@Ignore
	public void testRead2() throws Exception{
		this.mockmvc.perform(get("/board/read").param("bno", "500"))
		.andDo(print())
		.andExpect(status().is4xxClientError())
		.andExpect(handler().handlerType(BoardController.class))
		.andExpect(handler().methodName("read"));
	}
	@Test
	public void testUpdate() throws Exception{
		this.mockmvc.perform(post("/board/update")
				.param("bno", "2")
				.param("title", "수정")
				.param("content","수정")
				)
		.andDo(print())
		.andExpect(status().is3xxRedirection())
		.andExpect(handler().handlerType(BoardController.class))
		.andExpect(handler().methodName("updatePOST"));
	}
}
