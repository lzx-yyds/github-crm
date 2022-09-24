package com.lzx.crm.controller;

import com.lzx.crm.base.BaseController;
import com.lzx.crm.base.ResultInfo;
import com.lzx.crm.model.TreeModel;
import com.lzx.crm.service.ModuleService;
import com.lzx.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author lizixiang
 * @Date 2022/9/18 11:34
 * @Description:
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

    /**
     * @description: 进入授权页面
     * @author: lizixiang
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest request){
        request.setAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * @description: 查询资源列表
     * @author: lizixiang
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModuleList(){
        return moduleService.queryModuleList();
    }

    /**
     * @description: 进入资源管理页面
     * @author: lizixiang
     */
    @RequestMapping("index")
    public String index(){
        return "module/module";
    }

    /**
     * @description: 添加资源
     * @author: lizixiang
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addModule(Module module){
        moduleService.addModule(module);
        return success("添加资源成功！");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModule(id);
        return success("添加资源成功！");
    }


    /**
     * @description: 打开添加资源的页面
     * @author: lizixiang
     */
    @RequestMapping("toAddModulePage")
    public String toAddModulePage(Integer grade,Integer parentId,HttpServletRequest request){

        request.setAttribute("grade",grade);
        request.setAttribute("parentId",parentId);

        return "module/add";
    }

    /**
     * @description: 更新资源
     * @author: lizixiang
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("更新资源成功！");
    }

    /**
     * @description: 打开更新资源的页面
     * @author: lizixiang
     */
    @RequestMapping("toUpdateModulePage")
    public String toUpdateModulePage(Integer id, Model model){

        model.addAttribute("module",moduleService.selectByPrimaryKey(id));
        return "module/update";
    }
}
