package com.lzx.crm.dao;

import com.lzx.crm.base.BaseMapper;
import com.lzx.crm.model.TreeModel;
import com.lzx.crm.vo.Module;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module, Integer> {

     List<TreeModel> queryAllModeles();

     List<Module> queryModuleList();

    Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade,@Param("moduleName")  String moduleName);

    Module queryModuleByGradeAndUrl(@Param("grade")Integer garde,@Param("url") String url);

    Module queryModuleByOptValue(@Param("optValue")String optValue);

    Integer queryModuleByParentId(Integer id);
}