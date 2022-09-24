package com.lzx.crm.service;

import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.UserRoleMapper;
import com.lzx.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lizixiang
 * @Date 2022/9/17 17:34
 * @Description:
 */
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {

    @Resource
    private UserRoleMapper userRoleMapper;
}
