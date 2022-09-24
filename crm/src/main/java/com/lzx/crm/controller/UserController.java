package com.lzx.crm.controller;

import com.lzx.crm.base.BaseController;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.model.UserModel;
import com.lzx.crm.query.UserQuery;
import com.lzx.crm.service.UserService;
import com.lzx.crm.utils.LoginUserUtil;
import com.lzx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 登录功能
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd){

        ResultInfo resultInfo = new ResultInfo();

        // 通过try catch捕获service层异常，如果service层抛出异常，则表示登录失败，否则登录成功

        // 调用service层登录方法
        UserModel userModel =  userService.userLogin(userName,userPwd);

        // 设置ResultInfo的result的值（将数据返回给请求）
        resultInfo.setResult(userModel);
//
//        try{
//
//
//
//        }catch (ParamsException p){
//            // 传入异常
//            resultInfo.setCode(p.getCode());
//            resultInfo.setMsg(p.getMsg());
//            // 打印
//            p.printStackTrace();
//        }catch (Exception e){
//            // 传入异常
//            resultInfo.setCode(500);
//            resultInfo.setMsg("登录失败！");
//        }

        return resultInfo;
    }

    /**
     * 更新密码
     * @param request
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     * @return
     */
    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPwd(HttpServletRequest request, String oldPwd, String newPwd,String repeatPwd ){

        ResultInfo resultInfo = new ResultInfo();

        // 获取Cooke中用户id
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        // 调用service层修改密码的方法
        userService.updatePwdById(id, oldPwd, newPwd, repeatPwd);

//        try{
//
//
//
//        }catch (ParamsException p){
//            // 传入异常
//            resultInfo.setCode(p.getCode());
//            resultInfo.setMsg(p.getMsg());
//            // 打印
//            p.printStackTrace();
//        }catch (Exception e){
//            // 传入异常
//            resultInfo.setCode(500);
//            resultInfo.setMsg("修改密码失败！");
//            e.printStackTrace();
//        }
        return resultInfo;
    }

    /**
     * 进入修改页面
     * @return
     */
    @GetMapping("toPasswordPage")
    public String toPasswordPag(){
        return "user/password";
    }

    /**
     * @description: 查询所有销售人员
     * @author: lizixiang
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

    /**
     * @description: 分页多条件查询用户列表
     * @author: lizixiang
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(UserQuery userQuery){
        return userService.queryByParamsForTable(userQuery);
    }

    /**
     * @description: 进入用户列表页面
     * @author: lizixiang
     */
    @RequestMapping("index")
    public String index(){
        return "user/user";
    }

    /**
     * @description: 添加用户
     * @author: lizixiang
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("用户添加成功！");
    }

    /**
     * @description: 添加或修改用户页面
     * @author: lizixiang
     */
    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id, HttpServletRequest request){

        if (id != null) {
            User user = userService.selectByPrimaryKey(id);
            request.setAttribute("userInfo",user);
        }
        return "user/add_update";
    }

    /**
     * @description: 更新用户
     * @author: lizixiang
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    /**
     * @description: 用户删除
     * @author: lizixiang
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteByIds(ids);
        return success("用户删除成功！");
    }
}
