package per.khr.main.userservice.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(* per.khr.main.userservice.controller..*.*(..))")
    private void targetClass() {
    }

    @Around("targetClass()")
    public Object calcPerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();
        log.info("[시작] - targetClass: {}", pjp.getClass());

        Object result = pjp.proceed();

        sw.stop();
        log.info("[종료] - 걸린시간: {} ms", sw.getLastTaskTimeMillis());

        return result;
    }
}
