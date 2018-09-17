package com.gguri.swp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	//서비스 패키지에 있는 MessageSerivce의 메소드 모두(어떤 인자를 갖든)를 Pointcut으로 지정
	@Before("execution(* com.gguri.swp.service.MessageService*.*(..))")
	public void startLog(JoinPoint jp) {
		logger.info("-------------- startLog ---------------");
	}
}
