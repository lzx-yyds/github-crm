package com.lzx.crm.annoation;

import java.lang.annotation.*;

/**
 * @Author lizixiang
 * @Date 2022/9/18 19:11
 * @Description: 自定义注解，权限码
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {

    // 权限码
    String code() default "";
}
