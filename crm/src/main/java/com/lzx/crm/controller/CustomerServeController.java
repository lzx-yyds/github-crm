package com.lzx.crm.controller;

import com.lzx.crm.query.CustomerQuery;
import com.lzx.crm.query.CustomerServeQuery;
import com.lzx.crm.service.CustomerServeService;
import com.lzx.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/20 21:55
 * @Description:
 */
@Controller
@RequestMapping("customer_serve")
public class CustomerServeController {

    @Resource
    private CustomerServeService customerServeService;

    /**
     * @description: 分页查询服务
     * @author: lizixiang
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerServeQuery customerServeQuery){
        return customerServeService.querySaleChanceByParams(customerServeQuery);
    }

    /**
     * @description: 进入服务页面
     * @author: lizixiang
     */
    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if (type != null){
            if (type == 1){
                return "customerServe/customer_serve";
            }else if (type == 2){
                return "";
            }else if (type == 3){
                return "";
            }else if (type == 4){
                return "";
            }else if (type == 5){
                return "";
            }
        }else {
            return "";
        }

        return "";
    }
}
