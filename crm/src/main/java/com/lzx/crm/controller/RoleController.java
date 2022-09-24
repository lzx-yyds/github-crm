package com.lzx.crm.controller;

import com.lzx.crm.base.BaseController;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.query.RoleQuery;
import com.lzx.crm.service.RoleService;
import com.lzx.crm.utils.AssertUtil;
import com.lzx.crm.vo.Role;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/17 13:19
 * @Description:
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    /**
     * @description: 查询所有的角色列表
     * @author: lizixiang
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRole(Integer userId){
        return  roleService.queryAllRole(userId);
    }

    /**
     * @description: 多条件查询角色列表
     * @author: lizixiang
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> selectByPatams(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);
    }

    /**
     * @description: 进入角色管理页面
     * @author: lizixiang
     */
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    /**
     * @description: 添加角色
     * @author: lizixiang
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addRole(Role role){
        roleService.addRole(role);
        return success("角色添加成功！");
    }

    /**
     * @description: 添加/更新角色页面
     * @author: lizixiang
     */
    @RequestMapping("toAddOrUpdateRolePage")
    public String toAddOrUpdateRolePage(Integer roleId, HttpServletRequest request){

        if (roleId != null){
            Role role = roleService.selectByPrimaryKey(roleId);
            request.setAttribute("role",role);
        }
        return "role/add_update";
    }


    /**
     * @description: 修改角色
     * @author: lizixiang
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色更新成功！");
    }

    /**
     * @description: 删除角色
     * @author: lizixiang
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId){
        roleService.deleteRole(roleId);
        return success("角色删除成功！");
    }

    /**
     * @description: 角色授权
     * @author: lizixiang
     */
    @PostMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId, Integer[] mIds){

        roleService.addGrant(roleId, mIds);
        return success("角色授权成功！");
    }
}
