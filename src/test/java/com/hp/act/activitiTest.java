package com.hp.act;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class activitiTest {
    @Autowired
    private RepositoryService repositoryService;
    //部署流程
    @Test
    public void initDeploymentBPMN(){
        String fileName = "bpmn/hr_employee_holiday.bpmn";
        //将定义好的流程部署到activity
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(fileName)
                .name("员工请假流程")
                .deploy();
        //流程定义部署好后，可以获取到部署的信息
        System.out.println("流程部署的id-->" + deployment.getId());
        System.out.println("流程部署的时间-->" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deployment.getDeploymentTime()));
        System.out.println("流程部署名称-->" + deployment.getName());
    }

    @Autowired
    private RuntimeService runtimeService;
    @Test
    public void startProcessInstance(){
        //根据流程定义的key启动流程实例
        String processDefinitionKey = "employee_holiday2";
        //创建流程实例对象
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程定义的id--->" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id--->" + processInstance.getProcessInstanceId());
    }

    @Autowired
    private TaskService taskService;

    //查询个人待办任务
    @Test
    public void testPersonalTaskList(){
        String definitionKey = "employee_holiday2";
        //查询待办任务列表  (Assignee:受托人)
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(definitionKey)
                .taskAssignee("zs").list();
        //遍历任务列表
        tasks.forEach(task -> {
            System.out.println("任务id-->" + task.getId());
            System.out.println("任务的分配人-->" + task.getAssignee());
            System.out.println("任务的名称-->" + task.getName());
        });
    }

    //任务办理
    @Test
    public void compleTask(){
        String definitionKey = "employee_holiday2";
        //查询到待办任务
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(definitionKey)
                .taskAssignee("ww").list();
        System.out.println("------待办任务执行前任务列表-----");
        tasks.forEach(task -> {
            System.out.println("任务id-->" + task.getId());
            System.out.println("任务的分配人-->" + task.getAssignee());
            System.out.println("任务的名称-->" + task.getName());
        });

        //办理任务
        tasks.forEach(task -> {
            //根据任务id，办理任务
            taskService.complete(task.getId());
        });

        System.out.println("------办理任务后任务列表-----");
        tasks = taskService.createTaskQuery().processDefinitionKey(definitionKey)
                .taskAssignee("ww").list();
        tasks.forEach(task -> {
            System.out.println("任务id-->" + task.getId());
            System.out.println("任务的分配人-->" + task.getAssignee());
            System.out.println("任务的名称-->" + task.getName());
        });
    }
    //删除部署的流程定义信息
    @Test
    public void deleteDeployment(){
        //流程部署id
        String deploymentId="cccf188b-a30d-11ed-8943-e2c5d346bcc5";
        //删除流程定义，如果流程定义已经有流程实例则删除出错
        //repositoryService,deleteDeployment(deploymentId);
        //true:级联删除
        repositoryService.deleteDeployment(deploymentId,true);
    }
}
