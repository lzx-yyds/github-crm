package com.lzx.crm.controller;

import com.lzx.crm.annoation.RequiredPermission;
import com.lzx.crm.base.BaseController;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.enums.StateStatus;
import com.lzx.crm.query.SaleChanceQuery;
import com.lzx.crm.service.SaleChanceService;
import com.lzx.crm.utils.CookieUtil;
import com.lzx.crm.utils.LoginUserUtil;
import com.lzx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequiredPermission(code = "101001")
    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery, Integer flag, HttpServletRequest request){

        /**
         * @description: 客户开发计划
         * @author: lizixiang
         */
        if (flag != null && flag == 1 ){
            saleChanceQuery.setState(StateStatus.STATED.getType());
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            saleChanceQuery.setAssignMan(userId);
        }


        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    // 进入营销机会管理界面
    @RequiredPermission(code = "1010")
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    // 添加营销机会
    @RequiredPermission(code = "101002")
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request){

        String userName = CookieUtil.getCookieValue(request,"userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会添加成功！");
    }

    // 添加营销机会
    @RequiredPermission(code = "101004")
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance){

        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会更新成功！");
    }


    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer saleChanceId,HttpServletRequest request){
        if (saleChanceId != null){
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            // 将数据设置到请求域中
            request.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }

    /**
     * @description: 删除营销机会
     * @author: lizixiang
     */
    @RequiredPermission(code = "101003")
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChance(ids);
        return success("营销机会数据删除成功！");
    }

    /**
     * @description: 更新营销机会的开发状态
     * @author: lizixiang
     */
    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id, Integer devResult){

        saleChanceService.updateSaleChanceDevResult(id, devResult);

        return success("开发状态更新成功！");
    }
}
