package com.hp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.resp.RespBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//登录成功处理handler
@Component
public class HpAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //成功后处理的方法
        //成功后处理的方式
        response.setContentType("application/json;charset=UTF-8");//告诉浏览器，我们返回的是json
        response.getWriter().write(new ObjectMapper().writeValueAsString(RespBean.success("登录成功")));
    }
}
