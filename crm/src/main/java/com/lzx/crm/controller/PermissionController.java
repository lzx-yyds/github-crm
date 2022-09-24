package com.lzx.crm.controller;

import com.lzx.crm.base.BaseController;
import com.lzx.crm.service.PermissionService;
import com.lzx.crm.vo.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author lizixiang
 * @Date 2022/9/18 14:03
 * @Description:
 */
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;
}
