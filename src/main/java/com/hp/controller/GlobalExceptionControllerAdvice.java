package com.hp.controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public Object globalExceptionHandler(Exception e, HttpServletRequest request){
        Boolean isNew = (Boolean) request.getAttribute("isNew");
        if(isNew){
            //响应为视图
            request.setAttribute("message","系统异常");
            return "error";
        } else {
            //响应为json
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("code",500);
            mv.addObject("message",e.getMessage());
            return mv;
        }
    }
}
