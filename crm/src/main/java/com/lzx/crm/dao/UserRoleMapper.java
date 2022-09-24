package com.lzx.crm.dao;

import com.lzx.crm.base.BaseMapper;
import com.lzx.crm.vo.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    Integer countUserRoleByUserId(@Param("id") Integer id);

    Integer deleteUserRoleByUserId(@Param("id") Integer id);
}