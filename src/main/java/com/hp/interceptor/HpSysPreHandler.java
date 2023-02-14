package com.hp.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class HpSysPreHandler implements HandlerInterceptor {
    //在controller方法执行前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){//判断是否是方法,不是方法就是静态资源放行
            return true;//放行
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        //获取访问的资源
        Method method = handlerMethod.getMethod();
        //判断方法返回的结果
        boolean isString = method.getReturnType().equals(String.class);
        //判断方法上面是否标记有@ResponseBody
        boolean isResponseBody = !method.isAnnotationPresent(ResponseBody.class);
        //判断
        boolean isRestController = handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
        Boolean isNew = isRestController==true?false:(isString&&isResponseBody);//(我的理解：是json吗？是就返回false；否则是视图不是josn返回true，即是json返回false)
        request.setAttribute("isNew",isNew);//我的裂解：如果返回的是json，isNew为false
        //放行
        return true;
    }

    //在controller方法执行中执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //在controller方法执行后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
