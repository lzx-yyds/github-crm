package com.lzx.crm.dao;

import com.lzx.crm.base.BaseMapper;
import com.lzx.crm.vo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission, Integer> {

    Integer countPermissionByRoleId(Integer roleId);

    void deletePermissionByRoleId(Integer roleId);

    List<Integer> queryRoleHasModuleIdsByRoleId(@Param("roleId") Integer roleId);

    List<String> queryUserHasRoleHasPermissionByUserId(@Param("userId") Integer userId);

    Integer countPermissionByModuleId(Integer id);

    Integer deletePermissionByModuleId(Integer id);
}