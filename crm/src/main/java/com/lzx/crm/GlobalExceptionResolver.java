package com.lzx.crm;

import com.alibaba.fastjson.JSON;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.exceptions.AuthException;
import com.lzx.crm.exceptions.NoLoginException;
import com.lzx.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler, Exception ex) {

        // 非法请求拦截
        // 判断是否抛出登录异常
        if (ex instanceof NoLoginException){
            // 重定向到登录页面
            ModelAndView modelAndView = new ModelAndView("redirect:/index");
            return modelAndView;
        }

        // 默认异常情况  ModelAndView（视图）
        ModelAndView modelAndView = new ModelAndView("error");
        // 设置异常信息
        modelAndView.addObject("code",500);
        modelAndView.addObject("msg","异常异常，请重试...");

        // 判断HandlerMethod
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);

            if (responseBody == null){ // 返回视图，处理
                if (ex instanceof ParamsException){
                    ParamsException p = (ParamsException) ex;
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg",p.getMsg());
                }else if (ex instanceof AuthException){
                    AuthException p = (AuthException) ex;
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg",p.getMsg());
                }
                return modelAndView;

            }else { // 返回数据json，处理
                // 设置默认的异常处理
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("异常异常，请重试！");

                // 判断异常类型是否是自定义异常
                if (ex instanceof ParamsException){
                    ParamsException paramsException  = (ParamsException) ex;
                    resultInfo.setCode(paramsException.getCode());
                    resultInfo.setMsg(paramsException.getMsg());
                }else if (ex instanceof AuthException){
                    AuthException paramsException  = (AuthException) ex;
                    resultInfo.setCode(paramsException.getCode());
                    resultInfo.setMsg(paramsException.getMsg());
                }

                // 设置响应类型及编码格式（响应json格式的数据）
                response.setContentType("application/json;charset=UTF-8");

                // 得到字符输出流
                PrintWriter out = null;
                try {
                    // 得到输出流
                    out = response.getWriter();
                    // 将对象转换成json格式的字符
                    String json = JSON.toJSONString(resultInfo);
                    // 输出数据
                    out.write(json);

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (out != null){
                        out.close();
                    }
                }

                return null;
            }
        }

        return modelAndView;
    }
}
