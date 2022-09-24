package com.lzx.crm.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.ModuleMapper;
import com.lzx.crm.dao.PermissionMapper;
import com.lzx.crm.model.TreeModel;
import com.lzx.crm.utils.AssertUtil;
import com.lzx.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/18 11:33
 * @Description:
 */
@Service
public class ModuleService  extends BaseService<Module, Integer> {

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;
    /**
     * @description: 查询所有的资源列表
     * @author: lizixiang
     */
    public List<TreeModel> queryAllModules(Integer roleId){

         List<TreeModel> treeModelList = moduleMapper.queryAllModeles();

         List<Integer> permissionIds = permissionMapper.queryRoleHasModuleIdsByRoleId(roleId);

         if (permissionIds != null && permissionIds.size() > 0){
             treeModelList.forEach(treeModel -> {
                 if (permissionIds.contains(treeModel.getId())){
                     treeModel.setChecked(true);
                 }
             });
         }
         return treeModelList;
    }


    /**
     * @description:
     * @author: lizixiang
     */
    public Map<String, Object> queryModuleList(){
        Map<String, Object> map = new HashMap<>();

        List<Module> moduleList = moduleMapper.queryModuleList();
        map.put("code",0);
        map.put("msg","");
        map.put("count",moduleList.size());
        map.put("data",moduleList);

        return map;
    }

    /**
     * @description: 添加资源
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addModule(Module module){

        // 参数校验
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade ==1 || grade == 2),"菜单层级不合法！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名称不能为空！");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName()),"权限名已存在！");

        if (grade == 1 ){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"URL不能为空！");
            AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl()),"Url已存在!");
        }

        if (grade == 0){
            module.setParentId(-1);
        }

        if (grade != 0){
            AssertUtil.isTrue(null == module.getParentId(),"父级菜单不能为空！");
            AssertUtil.isTrue(null == moduleMapper.selectByPrimaryKey(module.getParentId()),"请指定父级菜单！");
        }

        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空！");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByOptValue(module.getOptValue()),"权限码已存在！");

        // 设置默认参数
        module.setIsValid((byte)1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());

        // 执行添加操作
        AssertUtil.isTrue(moduleMapper.insertSelective(module) < 1,"添加资源失败！");
    }

    /**
     * @description: 更新资源
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module){

        //参数校验
        AssertUtil.isTrue(null == module.getId(),"待更新记录不存在！");
        Module temp = moduleMapper.selectByPrimaryKey(module.getId());
        AssertUtil.isTrue(null == temp,"待更新记录不存在！");

        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2),"菜单层级不合法！");

        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名称不能为空！");
        temp = moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName());
        if (temp != null){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单已存在！");
        }

        if (grade == 1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"菜单URL不能为空！");
            temp = moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl());

            if (temp != null){
                AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单URL已存在");
            }
        }

        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限吗不能为空！");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        if (temp != null){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"权限码已存在");
        }

        //设置默认参数
        module.setUpdateDate(new Date());

        //执行更新操作
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module) < 1,"修改资源失败！");
    }

    /**
     * @description: 删除资源
     * @author: lizixiang
     */
    @Transactional(propagation =  Propagation.REQUIRED)
    public void deleteModule(Integer id){
        AssertUtil.isTrue(null == id,"待删除记录不存在！");
        Module temp = moduleMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == temp,"待删除记录不存在！");

        Integer count = moduleMapper.queryModuleByParentId(id);
        AssertUtil.isTrue(count > 0,"该资源存在子记录，不可删除！");

        count = permissionMapper.countPermissionByModuleId(id);

        if (count > 0){
            permissionMapper.deletePermissionByModuleId(id);
        }

        temp.setIsValid((byte) 0);
        temp.setUpdateDate(new Date());

        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(temp) < 1,"删除资源失败！");
    }

}
