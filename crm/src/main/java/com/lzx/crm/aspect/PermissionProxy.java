package com.lzx.crm.aspect;

import com.lzx.crm.annoation.RequiredPermission;
import com.lzx.crm.exceptions.AuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author lizixiang
 * @Date 2022/9/18 19:21
 * @Description:
 */
@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession httpSession;

    @Around(value = "@annotation(com.lzx.crm.annoation.RequiredPermission)")
    public Object around (ProceedingJoinPoint pjp) throws Throwable {

        Object result = null;
        List<String> permissions = (List<String>) httpSession.getAttribute("permissions");

        if (null == permissions || permissions.size()<1){
            throw new AuthException();
        }

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        RequiredPermission requiredPermission = methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);

        if (!(permissions.contains(requiredPermission.code()))){
            throw new  AuthException();
        }

        result = pjp.proceed();
        return result;
    }
}
