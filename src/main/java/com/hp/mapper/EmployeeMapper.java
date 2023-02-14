package com.hp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hp.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hp.req.EmployeeQuery;
import com.hp.vo.EmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    IPage<EmployeeVO> getEmployeeList(IPage page, @Param("employeeQuery")EmployeeQuery employeeQuery);

    /**
     * 根据empid，查询该用户的部门经理
     * @param id
     * @return
     */
    Employee queryDeptManagerByUserName(Integer id);

    /**
     * 查询老板
     * @return
     */
    List<Employee> findEmp(String titleName);
}
