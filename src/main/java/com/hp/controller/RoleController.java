package com.hp.controller;


import com.hp.pojo.Role;
import com.hp.resp.RespBean;
import com.hp.service.IAccountRoleService;
import com.hp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/index")
    public String index(){
        return "role/index";
    }

    //用户角色分配---跳转页面
    @RequestMapping("/assignUserPage")
    public String assignUserPage(Integer roleId, Model model){
        model.addAttribute("roleId",roleId);
        return "role/assign";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(){
        return roleService.findRoles();
    }

    @RequestMapping("/addOrUpdateRolePage")
    public String addOrUpdateRolePage(){
        return "role/add_update";
    }

    @RequestMapping("/save")
    @ResponseBody
    public RespBean saveRole(Role role){
        return roleService.saveRole(role);
    }

    @RequestMapping("/update")
    @ResponseBody
    public RespBean updateRole(Role role){
        return roleService.updateRole(role);
    }

    //打开选择用户页面
    @RequestMapping("/toSelectUserPage")
    public String toSelectUserPage(Integer roleId, Model model){
        model.addAttribute("roleId",roleId);
        return "role/assign_select_user";
    }

    @Autowired
    private IAccountRoleService accountRoleService;
    /**
     * 给用户分配角色
     */
    @RequestMapping("/assignRoleToUser")
    @ResponseBody
    public RespBean assignRoleToUser(Integer roleId,Integer eId){
        return accountRoleService.saveAccountRole(roleId,eId);
    }

    /**
     * 取消角色分配
     */
    @RequestMapping("/cancelRoleToUser")
    @ResponseBody
    public RespBean cancelRoleToUser(Integer roleId,Integer accountId){
        roleService.cancelRoleToUser(roleId,accountId);
        return RespBean.success("用户角色取消成功");
    }

    //权限展示页面
    @RequestMapping("/toAddGrantPage")
    public String toAddGrantPage(Integer roleId, Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     * 添加权限 （建立角色和菜单之间的关系）
     */
    @RequestMapping("/addGrant")
    @ResponseBody
    public RespBean addGrant(Integer[] mids,Integer roleId){
        roleService.addGrant(mids,roleId);
        return RespBean.success("权限添加成功");
    }
}
