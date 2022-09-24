package com.lzx.crm.service;

import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.PermissionMapper;
import com.lzx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lizixiang
 * @Date 2022/9/18 14:01
 * @Description:
 */
@Service
public class PermissionService extends BaseService<Permission ,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId) {

        return permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
    }
}
