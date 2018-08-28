package com.gguri.swp;


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
		int page = 6;
		int perPageNum = 10;
		
		UriComponents uriComponets = UriComponentsBuilder.newInstance()
				.path("/{module}/{page}")
				.queryParam("page", page)
				.queryParam("perPageNum", perPageNum)
				.queryParam("search","강원도 횡성군 쀍 ㅋㅋㅋ 인코딩 예제")
				.build()
				.expand("board","read")
				.encode();
		
		logger.info(uriComponets.toString());
	}
}
