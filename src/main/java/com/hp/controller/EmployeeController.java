package com.hp.controller;

import com.hp.pojo.Employee;
import com.hp.req.EmployeeQuery;
import com.hp.resp.RespBean;
import com.hp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/list")
    @ResponseBody

    public Map<String,Object> employeeList(EmployeeQuery employeeQuery){
        return employeeService.employeeList(employeeQuery);
    }

    @GetMapping("/index")
    public String toEmployeeList(){
        return "employee/index";
    }

    @RequestMapping("/save")
    @ResponseBody

    public RespBean saveEmployee(Employee employee){
        employeeService.saveEmployee(employee);
        return RespBean.success("员工添加成功");
    }

    /**
     * 进入到添加员工页面
     */
    @GetMapping("/addEmployeePage")
    public String addEmployeePage(){
        return "employee/add";
    }

    /**
     * 用户信息修改
     */
    @RequestMapping("/update")
    @ResponseBody

    public RespBean updateEmployee(Employee employee){
        employeeService.updateEmployee(employee);
        return RespBean.success("记录更新成功");
    }

    @RequestMapping("/delete")
    @ResponseBody

    public RespBean deleteEmployee(Integer id){
        employeeService.deleteEmployee(id);
        return RespBean.success("记录删除成功");
    }

    @RequestMapping("/updateEmployeePage")
    public String updateEmployeePage(Integer id,Map map){
        map.put("employee",employeeService.getById(id));
        return "employee/update";
    }
}
