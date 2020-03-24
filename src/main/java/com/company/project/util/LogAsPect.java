package com.company.project.util;


import com.company.project.configurer.Log;
import com.company.project.model.HostHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class LogAsPect {

    private final static Logger log = LoggerFactory.getLogger(LogAsPect.class);



    @Pointcut("@annotation(com.company.project.configurer.Log)")
    public void piontcut(){}

    @Pointcut("execution(public * com.company.project.web..*.*(..))")
    public void pointcutController() {}



    @Before("pointcutController()")
    public void around2(JoinPoint point) {

        //获取目标方法
        String methodNam = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();

        //获取方法参数
        String params = Arrays.toString(point.getArgs());

        log.info("method ： {}  params :{}",methodNam,params);
    }

    @Around("piontcut()")
    public Object around(ProceedingJoinPoint point){
        Object result = null;

        try{
            insertLog(point);
            result=point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    private void insertLog(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();


        Log userAction = method.getAnnotation(Log.class);


        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        // 请求的方法参数值
        String args = Arrays.toString(point.getArgs());

        //从session中获取当前登陆人id

//        int  userid = hostHolder.getUser().getUid() ;//应该从session中获取当前登录人的id，这里简单模拟下


        log.info("类名:{},方法名:{},参数：{}", className, methodName, args);


    }


}
