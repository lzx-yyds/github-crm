package com.lzx.crm.controller;

import com.lzx.crm.base.BaseController;
import com.lzx.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author lizixiang
 * @Date 2022/9/17 17:35
 * @Description:
 */
@Controller
@RequestMapping("userRole")
public class UserRoleController extends BaseController {

    @Resource
    private UserRoleService userRoleService;
}
