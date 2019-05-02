package io.zwt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //横切点（一系列Joint Point（连接点）的组合）
    @Pointcut("execution(public * io.zwt.aop.*.*(..))")
    public void logPointCut() {

    }

    //消息/加强（Advice）
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.error(request.getRequestURI());
        log.error("HTTP METHOD: " + request.getMethod());
        log.error("IP: " + request.getRemoteAddr());
        log.error("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "."
            + joinPoint.getSignature().getName());
        log.error("参数：" + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(World ret) {
        log.error("返回值：" + ret);
    }

    @Around("logPointCut()")
    public World doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        World object = (World) proceedingJoinPoint.proceed();
        log.error("耗时：" + (System.currentTimeMillis() - startTime));
        return object;
    }
}
