package com.gguri.swp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class URITestData {
	private static final Logger logger =
			LoggerFactory.getLogger(BoardDAOTest.class);
	@Test
	public void uriTest() {
		UriComponents uriComponets = null;
		for(int i = 0; i < 2000; i++) {
			uriComponets = UriComponentsBuilder.newInstance()
					.path("/{module}/{page}")
					.queryParam("keyword","강원도 고성군 토성면 케잌 뷐 ㄷㄹ지ㅏㅓㄷ질ㅈ더")
					.build()
					.expand("board","read")
					.encode();
			
		}
		logger.info(uriComponets.toString());
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