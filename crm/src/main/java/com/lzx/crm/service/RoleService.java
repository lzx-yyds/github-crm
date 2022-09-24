package com.lzx.crm.service;

import com.lzx.crm.base.BaseService;
import com.lzx.crm.dao.ModuleMapper;
import com.lzx.crm.dao.PermissionMapper;
import com.lzx.crm.dao.RoleMapper;
import com.lzx.crm.utils.AssertUtil;
import com.lzx.crm.vo.Permission;
import com.lzx.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/17 13:13
 * @Description:
 */
@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleMapper moduleMapper;

    /**
     * @description: 查询所有的角色列表
     * @author: lizixiang
     */
    public List<Map<String,Object>> queryAllRole(Integer userId){
        return  roleMapper.queryAllRole(userId);
    }

    /**
     * @description: 添加用户角色
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role){
        // 参数校验
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空！");
        Role temp = roleMapper.selectByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp != null,"角色名称已存在，请重新输入！");

        // 设置默认参数
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());

        //执行添加操作
        AssertUtil.isTrue(roleMapper.insertSelective(role) < 1,"角色添加失败！" );

    }

    /**
     * @description: 编辑/更新角色
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role){
        AssertUtil.isTrue(null == role.getId(),"待更新记录不存在！");
        Role temp = roleMapper.selectByPrimaryKey(role.getId());
        AssertUtil.isTrue(null == temp,"待更新记录不存在！");

        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空！");
        temp = roleMapper.selectByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp && (!temp.getId().equals(role.getId())),"用户角色已存在，不可使用该用户名");

        role.setUpdateDate(new Date());

        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) <  1,"修改角色失败！");
    }


    /**
     * @description: 删除角色
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void  deleteRole(Integer roleId){
        AssertUtil.isTrue(null == roleId,"待删除记录不存在！");
        Role role = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null == role,"待删除记录不存在！");

        role.setIsValid(0);
        role.setUpdateDate(new Date());

        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1,"角色删除失败！");

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer roleId, Integer[] mIds) {
        Integer count = permissionMapper.countPermissionByRoleId(roleId);

        if (count > 0){
            permissionMapper.deletePermissionByRoleId(roleId);
        }

        if (mIds != null && mIds.length > 1){
            List<Permission> permissionList = new ArrayList<>();

            for (Integer mId: mIds){
                Permission permission = new Permission();
                permission.setModuleId(mId);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mId).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());

                permissionList.add(permission);
            }

            AssertUtil.isTrue(permissionMapper.insertBatch(permissionList) != permissionList.size(),"角色授权失败！");
        }
    }
}
