package com.lzx.crm.interceptors;

import com.lzx.crm.dao.UserMapper;
import com.lzx.crm.exceptions.NoLoginException;
import com.lzx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);

        if (null == userId || userMapper.getKeyById(userId) == null){
            throw  new NoLoginException();
        }

        return true;
    }
}
