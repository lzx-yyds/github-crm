package com.lzx.crm.controller;

import com.lzx.crm.query.CustomerQuery;
import com.lzx.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/20 19:56
 * @Description:
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * @description: 分页查询客户列表
     * @author: lizixiang
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery){
        return customerService.querySaleChanceByParams(customerQuery);
    }

    /**
     * @description: 进入客户信息管理页面
     * @author: lizixiang
     */
    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }
}
