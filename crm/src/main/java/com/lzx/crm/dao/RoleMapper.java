package com.lzx.crm.dao;

import com.lzx.crm.base.BaseMapper;
import com.lzx.crm.vo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    // 查询所有角色列表
    List<Map<String,Object>> queryAllRole(@Param("userId") Integer userId);

    Role selectByRoleName(@Param("roleName") String roleName);
}