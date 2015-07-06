package com.bbeerr.core.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ViewAspect {

	static Logger logger = LogManager.getLogger(ViewAspect.class.getName());

	public ViewAspect() {

	}

	@Around("execution(* org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.handle(..))")
	public Object uri(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = pjp.proceed();
		return retVal;
	}

}