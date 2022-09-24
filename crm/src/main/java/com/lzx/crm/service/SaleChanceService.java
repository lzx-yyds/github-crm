package com.lzx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.SaleChanceMapper;
import com.lzx.crm.enums.DevResult;
import com.lzx.crm.enums.StateStatus;
import com.lzx.crm.query.SaleChanceQuery;
import com.lzx.crm.utils.AssertUtil;
import com.lzx.crm.utils.PhoneUtil;
import com.lzx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {

        Map<String, Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());

        // 得到对应分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));

        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data", pageInfo.getList());

        return map;
    }

    // 添加营销机会
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        // 校验参数
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        // 设置相关字段默认值
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());

        // 判断是否设置了指派人
        if (StringUtils.isBlank(saleChance.getAssignMan())){
            // 若空
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }else {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }

        // 执行添加操作
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) != 1,"添加营销机会失败！");
    }

    // 更新营销机会
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        AssertUtil.isTrue(null == saleChance.getId(),"待更新记录不存在！");

        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());

        AssertUtil.isTrue(temp == null,"待更新记录不存在！");

        // 校验参数
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());

        saleChance.setUpdateDate(new Date());

        if (StringUtils.isBlank(temp.getAssignMan())){
            if (!StringUtils.isBlank(saleChance.getAssignMan())){// 修改前为空，修改后有值
                saleChance.setAssignTime(new Date());
                // 1=已分配
                saleChance.setState(StateStatus.STATED.getType());
                // 1=开发中
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        }else {
            if (StringUtils.isBlank(saleChance.getAssignMan())){// 修改前有值，修改后无值
                saleChance.setAssignTime(null);
                // 0=未分配
                saleChance.setState(StateStatus.UNSTATE.getType());
                // 0=未开放
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            }else {// 修改前有值，修改后有值
                // 判断是否为同一个用户
                if (!saleChance.getAssignMan().equals(temp.getAssignMan())){
                    saleChance.setAssignTime(new Date());
                }else {
                    saleChance.setAssignTime(temp.getAssignTime());
                }
            }
        }

        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) != 1,"更新营销机会失败！" );
    }


    /**
     * @description: 删除营销机会
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(null == ids || ids.length < 1,"待删除记录不存在！");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids) != ids.length,"营销机会数据删除失败！");
    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系号码不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"联系号码格式不正确！");
    }

    /**
     * @description: 更新营销机会开发状态
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {

        AssertUtil.isTrue(null == id,"待更新记录不存在！");

        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);

        AssertUtil.isTrue(null == saleChance,"待更新记录不存在");

        saleChance.setDevResult(devResult);

        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) != 1,"开发状态更新失败！");
    }
}
