package com.gguri.swp.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
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
		logger.info("pointcut >> " + jp.getSignature().getName());
		logger.info(" args >> " + Arrays.toString(jp.getArgs()));
	}
	@After("execution(* com.gguri.swp.service.MessageService*.*(..))")
	public void endLog(JoinPoint jp) {
		logger.info("-------------- endLog ---------------");
		logger.info("pointcut >> " + jp.getSignature().getName());
		logger.info(" args >> " + Arrays.toString(jp.getArgs()));
	}
	@Around("execution(* com.gguri.swp.service.MessageService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable{
		logger.info("-------------- TimeLog ---------------");
		long stime = System.currentTimeMillis();
		Object result = pjp.proceed();
		logger.info(pjp.getSignature().getName() + ">>" + (System.currentTimeMillis() - stime));
		logger.info("-------------- TimeLog ---------------");
		return result;
	}
}
