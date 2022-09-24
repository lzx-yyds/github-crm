package com.lzx.crm.controller;


import com.lzx.crm.base.BaseController;
import com.lzx.crm.service.PermissionService;
import com.lzx.crm.service.UserService;
import com.lzx.crm.utils.LoginUserUtil;
import com.lzx.crm.vo.Permission;
import com.lzx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

         /**
          * 系统登录页
          * @return
          */
         @RequestMapping("index")
        public String index(){
            return "index";
            }

         // 系统界面欢迎页
         @RequestMapping("welcome")
         public String welcome(){
             return "welcome";
            }

         /**
          * 后端管理主页面
          * @return
          */
         @RequestMapping("main")
         public String main(HttpServletRequest request){

             // 获取cookie中的用户id
             Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);

             // 查询用户对象，设置session作用域
             User user = userService.selectById(userId);

             request.getSession().setAttribute("user",user);

             List<String> permissions = permissionService.queryUserHasRoleHasPermissionByUserId(userId);

             request.getSession().setAttribute("permissions",permissions);

             return "main";
          }


}



