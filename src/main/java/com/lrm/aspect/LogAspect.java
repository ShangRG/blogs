package com.lrm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志aop切面类    在执行controller某个方法前想要记录日志，所以  用切面来做
 * @Aspect：标明这是一个aop 切面
 * @Component： 开启组建扫描 springboot能找到这个类
 */
@Aspect
@Component
public class LogAspect {

     private final Logger logger=LoggerFactory.getLogger(this.getClass());

    /**
     * 切面方法
     * @Pointcut：标明这是一个切面方法
     * exception():拦截那些类    给那些类进行切面增加  * com.lrm.web.* ：对这个包下的所以类的所有方法进行切面
     */
    @Pointcut("execution(* com.lrm.web.*.*(..))")
    public void log() {}

    /**
     * 执行controller前，先记录日志
     * JoinPoint：这里包含 请求参数  和  方法名  类名
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获取request 因为这里有 请求 url 和ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("---------doAfter-----------");
    }

    /**
     * 方法执行完后可以给日志中记录方法的参数等等
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result){
        logger.info("Result: {}",result);

    }

    private class RequestLog{

        private String url; //地址
        private String ip; //ip
        private String classMethod;  //类名，方法名
        private Object[] args; //参数

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}

