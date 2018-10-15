package com.gguri.mapper;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class SampleMapperTest {
	
	@Inject
	private SampleMapper sampleMapper;
	
	@Test
	public void testGetTime() {
		String className = sampleMapper.getClass().getName();
		System.out.println("className=" + className);
		String now =sampleMapper.getTime();
		System.out.println("now=" + now);
		assertTrue(StringUtils.startsWith(className, "com.sun.proxy."));
	}
}
