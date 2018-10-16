package com.gguri.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gguri.swp.domain.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class SampleMapperTest {
	
	@Inject
	private SampleMapper sampleMapper;
	
	@Test
	public void testGetTime() throws Exception{
		String className = sampleMapper.getClass().getName();
		System.out.println("className=" + className);
		String now =sampleMapper.getTime();
		System.out.println("now=" + now);
		assertTrue(StringUtils.startsWith(className, "com.sun.proxy."));
	}
	@Test
	public void testSearch() throws Exception{
		String searchCol = "uid";
		String searchStr = "user1";
		
		List<UserVO> users = sampleMapper.serachUser(searchCol, searchStr);
		assertEquals(1, users.size());
		assertEquals(searchStr, users.get(0).getUid());
		
		searchCol = "uname";
		searchStr = "구경민";
		
		users = sampleMapper.serachUser(searchCol, searchStr);
		assertEquals(1, users.size());
		assertEquals(searchStr, users.get(0).getUname());
	}
}
