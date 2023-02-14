package com.hp.controller;


import com.hp.dto.DeptDto;
import com.hp.pojo.Dept;
import com.hp.resp.RespBean;
import com.hp.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private IDeptService deptService;
    @RequestMapping("/index")
    public String index(){
        return "dept/index";
    }

    //进入到添加页面
    @GetMapping("/addDeptPage")
    public String addOrUpdatePage(Integer level,Integer parentId,Map<String,Object> map){
        //根据父id，查询父部门
        map.put("parentDept",deptService.getById(parentId));
        map.put("parentId",parentId);
        map.put("level",level);
        return "dept/add";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> deptList(){
        return deptService.deptList();
    }


    /**
     * 添加部门
     * @param dept
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    @PreAuthorize("hasAuthority('100871')")
    public RespBean saveDept(Dept dept){
        deptService.saveDept(dept);
        return RespBean.success("部门添加成功");
    }

    //更新部门表单页面
    @GetMapping("/updateDeptPage")
    public String updateDeptPage(Integer id, Model model){
        Dept dept = deptService.getById(id);//根据id获取到要修改的对象
        model.addAttribute("dept",dept);
        model.addAttribute("parentDept",deptService.getById(dept.getParentId()));
        return "dept/update";
    }

    //修改部门
    @RequestMapping("/update")
    @ResponseBody
    @PreAuthorize("hasAuthority('100873')")
    public RespBean updateDept(Dept dept){
        deptService.updateDept(dept);
        return RespBean.success("记录更新成功");
    }

    /**
     * 删除部门
     */
    @RequestMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('100874')")
    public RespBean deleteDept(Integer id){
        deptService.deleteDept(id);
        return RespBean.success("部门删除成功");
    }

    @RequestMapping("/findAllDept")
    @ResponseBody
    @PreAuthorize("hasAuthority('100872')")
    public List<DeptDto> findAllDept(){
        return deptService.findAllDept();
    }
}
