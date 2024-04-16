package com.x;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @date 2022/6/22 17:35
 */
// @Aspect
@Slf4j
public class Aop {
    public Aop(){
        log.info("------------ Aop init");
    }

    @Around("target(com.x.IUser) && execution(public void info(..))")
    public Object loggerAround(ProceedingJoinPoint jp) throws Throwable {
        log.info("------------- {}", jp.getSignature().getName());
        return jp.proceed();
    }
}
