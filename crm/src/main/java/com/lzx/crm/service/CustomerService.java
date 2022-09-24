package com.lzx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.CustomerMapper;
import com.lzx.crm.query.CustomerQuery;
import com.lzx.crm.query.SaleChanceQuery;
import com.lzx.crm.vo.Customer;
import com.lzx.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/20 19:54
 * @Description:
 */
@Service
public class CustomerService extends BaseService<Customer, Integer> {

    @Resource
    private CustomerMapper customerMapper;

    /**
     * @description: 查询客户
     * @author: lizixiang
     */
    public Map<String, Object> querySaleChanceByParams(CustomerQuery customerQuery) {

        Map<String, Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit());

        // 得到对应分页对象
        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(customerQuery));

        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());

        return map;
    }

}
