package com.lzx.crm.dao;

import com.lzx.crm.base.BaseMapper;
import com.lzx.crm.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserbyName(@Param("userName") String userName);

    User selectById(@Param("userId") Integer userID);

    void updatePwdById(@Param("newPwd") String newPwd,@Param("id") Integer id);

    Integer getKeyById(@Param("userId") Integer userId);

    // 查询所有的销售人员
    List<Map<String,Object>> queryALLSales();
}