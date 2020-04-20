package com.lrm.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
/**
 * 自定义异常拦截器
 */
@ControllerAdvice   //拦截标注有controller注解的控制器
public class ControllerExceptiomHandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    /**
     *  @ExceptionHandler:加这个注解用来拦截异常的  Exception.class：处理异常级别为Exception.
     *  @ExceptionHandler ： 只要加上这个注解以为这controller那边只要出现Exception异常就会拦截来这里进行处理
     *  ModelAndView：用来返回页面并且可以携带信息
     * @param request ： 用来获取那些路径出问题
     * @param e ： 报的是什么错
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandel(HttpServletRequest request,Exception e) throws Exception {
        //把错误信息记录到日志
        logger.error("Request URL:{},Exception:{}",request.getRequestURL(),e);

        //因为这个方法标识了@ExceptionHandler,只要controller出现异常都会来这里，但是我们希望只是
        //服务器内部我们来处理，如果是404的话 交由spring本身处理  也就是下面这个if实现
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        //携带错误信息  相当于 request.setAu....
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        //要返回到那个页面
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
