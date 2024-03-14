package com.example.potatotilnewsfeed.domain.user.aop.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class TimerAop {

      @Around("@annotation(Timer)")
      public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

          //메서드 시작 전
          StopWatch stopWatch = new StopWatch();
          stopWatch.start();

          //메서드가 실행되는 지점
          try {
              return joinPoint.proceed();
          } finally {

              //메서드 종료 후
              stopWatch.stop();

              log.info("총 걸린 시간: " + stopWatch.getTotalTimeSeconds());
          }
      }

}


