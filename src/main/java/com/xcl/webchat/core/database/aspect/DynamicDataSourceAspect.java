package com.xcl.webchat.core.database.aspect;

import com.xcl.webchat.core.database.config.DynamicDataSourceHolder;
import com.xcl.webchat.core.database.config.TargetDataSource;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author :xiaochanglu
 * @Description :
 * @date :2019/3/16 15:41
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    @Pointcut("@annotation(com.xcl.webchat.core.database.config.TargetDataSource)")
    public void point() {
    }

    @Pointcut("execution(public * com.xcl.webchat.apply.*.service..*.*(..))")
    public void excudeService() {
    }

    @Around(value = "point()&&excudeService()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(TargetDataSource.class)) {
            String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).dataSource();
            DynamicDataSourceHolder.setDataSource(targetDataSource);
        }
        // 执行方法
        Object result = pjp.proceed();
        DynamicDataSourceHolder.clearDataSource();
        return result;
    }
}
