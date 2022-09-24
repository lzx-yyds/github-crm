package com.lzx.crm.controller;

import com.lzx.crm.base.BaseController;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.query.CusDevPlanQuery;
import com.lzx.crm.service.CusDevPlanService;
import com.lzx.crm.service.SaleChanceService;
import com.lzx.crm.utils.AssertUtil;
import com.lzx.crm.vo.CusDevPlan;
import com.lzx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/8 21:16
 * @Description:
 */
@RequestMapping("cus_dev_plan")
@Controller
public class CusDevPlanController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    /**
     * @description: 进入客户开发计划页面
     * @author: lizixiang
     */
    @RequestMapping("index")
    public String index(){
        return "cusDevPlan/cus_dev_plan";
    }

    /**
     * @description: 打开计划项开发与详情页面
     * @author: lizixiang
     */
    @RequestMapping("toCusDevPlanPage")
    public String toCusDevPlanPage(Integer id, HttpServletRequest request) {

        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        request.setAttribute("saleChance", saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    /**
     * @description: 客户开发计划数据查询（分页多条件查询）
     * @author: lizixiang
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryCusDevPlanByParams(cusDevPlanQuery);
    }

    /**
     * @description: 添加计划项
     * @author: lizixiang
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划项添加成功！");
    }

    /**
     * @description: 更新计划项
     * @author: lizixiang
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项更新成功！");
    }

    /**
     * @description: 删除计划项
     * @author: lizixiang
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("计划项删除成功！");
    }

    /**
     * @description: 进入添加或修改计划项的页面
     * @author: lizixiang
     */
    @RequestMapping("toAddOrUpdateCusDevPlanPage")
    public String toAddOrUpdateCusDevPlanPage(HttpServletRequest request, Integer sId, Integer id){
        request.setAttribute("sId", sId);

        CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
        request.setAttribute("cusDevPlan", cusDevPlan);

        return "cusDevPlan/add_update";
    }
}
