package com.hp.controller;

import com.hp.req.MyTaskQuery;
import com.hp.service.IHolidayApplyService;
import com.hp.utils.SpringSecurityUtils;
import com.hp.wf.WorkflowConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/workBench")
public class MyWorkBenchController {
    @Autowired
    private IHolidayApplyService holidayApplyService;
    /**
     * 进入到我的工作台页面
     * @return
     */
    @RequestMapping("/toTaskListPage")
    public String toTaskListPage(){
        return "workBench/task_list_page";
    }

    //查询我的待办任务
    @RequestMapping("/queryMyTaskList")
    @ResponseBody
    public Map<String,Object> queryMyTaskList(MyTaskQuery myTaskQuery){
        //获取到当前登录人的name
        myTaskQuery.setUserName(SpringSecurityUtils.getAccountName());
        System.out.println(SpringSecurityUtils.getAccountName());
        myTaskQuery.setProcessDefinitionKey(WorkflowConstant.EMPLOYEE_HOLIDAY_PROCESS_DEFINITION_KEY);
        System.out.println(holidayApplyService.queryMyTaskList(myTaskQuery));
        return holidayApplyService.queryMyTaskList(myTaskQuery);
    }
}
