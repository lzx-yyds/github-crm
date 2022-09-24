package com.lzx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.CustomerServeMapper;
import com.lzx.crm.query.CustomerServeQuery;
import com.lzx.crm.vo.Customer;
import com.lzx.crm.vo.CustomerServe;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/20 21:53
 * @Description:
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe, Integer> {

    @Resource
    private CustomerServeMapper customerServeMapper;

    /**
     * @description: 查询服务
     * @author: lizixiang
     */
    public Map<String, Object> querySaleChanceByParams(CustomerServeQuery customerServeQuery) {

        Map<String, Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(customerServeQuery.getPage(), customerServeQuery.getLimit());

        // 得到对应分页对象
        PageInfo<CustomerServe> pageInfo = new PageInfo<>(customerServeMapper.selectByParams(customerServeQuery));

        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());

        return map;
    }

}
