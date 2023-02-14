package com.hp.controller;


import com.hp.pojo.EmployeeStatus;
import com.hp.service.IEmployeeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
@RestController
@RequestMapping("/employeeStatus")
public class EmployeeStatusController {


    @Autowired
    private IEmployeeStatusService employeeStatusService;

    @RequestMapping("/queryAllEmployeeStatus")
    public List<EmployeeStatus> queryAllEmployeeStatus(){
        return employeeStatusService.queryAllEmployeeStatus();
    }
}
