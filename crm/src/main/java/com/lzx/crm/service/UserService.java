package com.lzx.crm.service;

import com.lzx.crm.base.BaseService;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.dao.UserMapper;
import com.lzx.crm.dao.UserRoleMapper;
import com.lzx.crm.model.UserModel;
import com.lzx.crm.utils.AssertUtil;
import com.lzx.crm.utils.Md5Util;
import com.lzx.crm.utils.PhoneUtil;
import com.lzx.crm.utils.UserIDBase64;
import com.lzx.crm.vo.User;
import com.lzx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 用户登录
     */
    public UserModel userLogin(String userName, String userPwd){

        // 检查登录用户、密码是否为空
        checkLoginParams(userName, userPwd);

        // 调用数据访问层，通过用户名查询用户记录，返回用户对象
        User user = userMapper.queryUserbyName(userName);

        // 判断用户对象是否为空
        AssertUtil.isTrue(user == null,"该用户不存在!");

        // 密码校验
        checkUserPwd(userPwd,user.getUserPwd());

        // 返回用户对象
        return buildUSerInfo(user);


    }

    // 返回用户对象
    private UserModel buildUSerInfo(User user) {

        UserModel userModel = new UserModel();
        userModel.setUserId(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());

        return  userModel;
    }

    // 密码校验
    private void checkUserPwd(String userPwd, String password) {
        // 先加密输入的密码，再检验
        userPwd = Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(password),"用户密码不正确！");

    }

    // 账号密码非空判断
    private void checkLoginParams(String userName,String userPwd){
        // 验证用户姓名
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户姓名不能为空!");
        // 验证用户密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空!");


    }


    /**
     * 根据id获取用户信息
     */

    public User selectById(Integer userId){
        return userMapper.selectById(userId);
    }

    /**
     * 根据id修改密码
     * 3. 参数校验
     *                 待更新用户记录是否存在 （用户对象是否为空）
     *                 判断原始密码是否为空
     *                 判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
     *                 判断新密码是否为空
     *                 判断新密码是否与原始密码一致 （不允许新密码与原始密码）
     *                 判断确认密码是否为空
     *                 判断确认密码是否与新密码一致
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePwdById(Integer id, String oldPwd, String newPwd, String repeatPwd){

        // 通过id查询用户记录，返回用户对象
        User user = userMapper.selectById(id);
        // 判断用户记录是否存在
        AssertUtil.isTrue(null == user,"该用户不存在！");

        // 判断原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空！");
        // 判断原始密码是否正确
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)),"原始密码不正确！");

        // 判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空！");
        // 判断新密码是否与原始密码一致
        AssertUtil.isTrue(oldPwd.equals(newPwd),"输入的原始密码和新密码不能一致！");

        // 判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"输入的确认密码不能为空！");
        // 判断新密码和确认密码是否一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd),"新密码和确认密码不一致！");

        // 设置用户密码
        user.setUserPwd(Md5Util.encode(newPwd));
        // 执行更新操作
        userMapper.updatePwdById(user.getUserPwd(),id);


    }


    /**
     * @author: lizixiang
     * @description: 查询所有销售人员
     */
    public List<Map<String,Object>> queryAllSales(){
        return  userMapper.queryALLSales();
    }

    /**
     * @description: 添加用户
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user){
        // 参数校验
        checkUserParams(user.getUserName(),user.getPhone(),user.getEmail(),null);
        // 设置默认值
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));

        // 执行添加操作
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1,"用户添加失败！");

        //用户角色关联
        relationUserRole(user.getId(),user.getRoleIds());
    }

    //用户角色关联
    private void relationUserRole(Integer id, String roleIds) {

        Integer count = userRoleMapper.countUserRoleByUserId(id);

        if (count > 0 ){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(id) != count,"用户角色分配失败！");
        }

        if (StringUtils.isNotBlank(roleIds)){
            List<UserRole> userRoleList = new ArrayList<>();
            String[] roleIdsArray = roleIds.split(",");
            for (String roleId : roleIdsArray){
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(id);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());

                userRoleList.add(userRole);
            }

            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList ) != userRoleList.size(),"用户角色关联失败！");
        }


    }

    /**
     * @description: 更新用户
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        AssertUtil.isTrue(null == user.getId(),"待更新记录不存在！");
        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(null == temp,"待更新记录不存在！");

        checkUserParams(user.getUserName(),user.getPhone(),user.getEmail(),user.getId());

        user.setUpdateDate(new Date());

        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)!= 1,"用户更新失败！");

        //用户角色关联
        relationUserRole(user.getId(),user.getRoleIds());
    }

    /**
     * @description: 校验参数
     * @author: lizixiang
     */
    private void checkUserParams(String userName, String phone, String email,Integer id) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");

        User temp = userMapper.queryUserbyName(userName);
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(id)),"用户名已存在，请重新输入！");

        AssertUtil.isTrue(StringUtils.isBlank(email),"用户邮箱不能为空！");

        AssertUtil.isTrue(StringUtils.isBlank(phone),"用户手机号不能为空！");

        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号格式不正确！");
    }

    /**
     * @description: 删除用户
     * @author: lizixiang
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids){
        AssertUtil.isTrue(ids == null || ids.length == 0,"待删除记录不存在！");
        AssertUtil.isTrue(userMapper.deleteBatch(ids) != ids.length,"用户删除失败！");

        for (Integer userId : ids){

            Integer count = userRoleMapper.countUserRoleByUserId(userId);

            if (count > 0){
                AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count,"用户删除失败！");
            }
        }
    }



}


