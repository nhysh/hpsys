package com.hp.service;

import com.hp.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hp.req.EmployeeQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
public interface IEmployeeService extends IService<Employee> {
    public Map<String,Object> employeeList(EmployeeQuery employeeQuery);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(Integer id);

    Employee queryDeptManagerByUserName(Integer empId);

    Employee findBoss();

    List<Employee> findAllHrs();
}
