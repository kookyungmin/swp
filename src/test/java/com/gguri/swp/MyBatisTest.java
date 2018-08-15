package com.gguri.swp;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Runner 클래스(테스트 메소드를 실행하는 클래스) 를 SpringJUnit4ClassRunner로 함
@RunWith(SpringJUnit4ClassRunner.class)
//location 속성 경로에 있는 xml 파일을 이용해서 스프링이 로딩됨
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")

public class MyBatisTest {
	
	//SqlSessionFactory 객체를 자동으로 생성
	@Inject
	private SqlSessionFactory sqlFactory;
	
	//SqlSessionFactory 객체가 제대로 만들어졌는지 Test
	@Test
	public void testFactory() {
		System.out.println(sqlFactory);
	}
	
	//MyBatis와 Mysql 서버가 제대로 연결되었는지 Test
	@Test
	public void testSession() throws Exception{
		try(SqlSession session = sqlFactory.openSession()){
			System.out.println(session);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}