package com.hp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hp.pojo.Account;
import com.hp.pojo.Employee;
import com.hp.mapper.EmployeeMapper;
import com.hp.req.EmployeeQuery;
import com.hp.service.IAccountService;
import com.hp.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.utils.AssertUtil;
import com.hp.utils.PageResultUtil;
import com.hp.vo.EmployeeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Override
    public Map<String, Object> employeeList(EmployeeQuery employeeQuery) {
        IPage<EmployeeVO> page = new Page<>(employeeQuery.getPage(),employeeQuery.getLimit());
        page = this.baseMapper.getEmployeeList(page, employeeQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAccountService accountService;

    @Override
    //                           必须有事务                        遇到异常就停止
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveEmployee(Employee employee) {
        //基本信息验证
        checkParam(employee.getEmpName(),employee.getMobile(),employee.getEmail());
        //要求邮箱一定是唯一的
        Employee temp = this.findEmployeeByEmail(employee.getEmail());
        AssertUtil.isTrue(null != temp,"该邮箱已经被绑定");
        //获取到员工的工号
        String empNum = this.generateEmpNum();
        employee.setEmpNum(empNum);
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        employee.setFormalStatus("1");//默认已转正
        employee.setStatus(1);
        AssertUtil.isTrue(!(this.save(employee)),"记录添加失败!");

        //创建账号对象
        Account account = new Account();
        account.setEmpId(employee.getId());
        account.setPassword(passwordEncoder.encode("123"));//新用户的默认密码是123
        account.setUserName(employee.getEmail());//用email作为用户名
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        AssertUtil.isTrue(!(accountService.save(account)),"记录添加失败");

    }

    @Override
    public void updateEmployee(Employee employee) {
        //查询要修改的用户是否存在
        Employee temp = this.getOne(new QueryWrapper<Employee>().eq("id", employee.getId()).eq("status", 1));
        AssertUtil.isTrue(null == temp,"待更新的记录不存在");
        //校验属性
        checkParam(employee.getEmpName(),employee.getMobile(),employee.getEmail());
        temp = this.findEmployeeByEmail(employee.getEmail());
        //修改的email也不能和已经存在的email重复
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(employee.getId())),"该邮箱已经被绑定");
        employee.setUpdateTime(new Date());//修改更新时间
        AssertUtil.isTrue(!(this.updateById(employee)),"记录更新失败!");


        //----------------账户更新
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("emp_id", employee.getId()));
        if(!(account.getUsername().equals(employee.getEmail()))){
            account.setUserName(employee.getEmail());
            account.setUpdateTime(new Date());
            AssertUtil.isTrue(!accountService.updateById(account),"记录更新失败!");
        }
    }

    @Override
    public void deleteEmployee(Integer id) {
        //要删除的用户是否存在
        Employee employee = this.getOne(new QueryWrapper<Employee>()
                .eq("id", id).eq("status", 1));
        AssertUtil.isTrue(null == employee,"待删除的记录不存在");
        //删除是做逻辑删除，将status设置为0
        employee.setStatus(0);
        AssertUtil.isTrue(!(this.updateById(employee)),"记录删除失败!");

        //账户状态也需要修改
        Account account = this.accountService.findAccountByUserName(employee.getEmail());
        account.setStatus(0);//执行逻辑删除
        AssertUtil.isTrue(!(this.accountService.updateById(account)),"记录删除失败");
    }

    @Override
    public Employee queryDeptManagerByUserName(Integer empId) {
        return this.baseMapper.queryDeptManagerByUserName(empId);
    }

    @Override
    public Employee findBoss() {
        return this.baseMapper.findEmp("老板").get(1);
    }

    @Override
    public List<Employee> findAllHrs() {
        return this.baseMapper.findEmp("人事");
    }

    /**
     * 创建员工工号
     * @return
     */
    private String generateEmpNum() {
        String empNum = "000001";
        String maxEmpNum = this.baseMapper.selectOne(new QueryWrapper<Employee>()
                .select("max(emp_num) as empNum")).getEmpNum();
        if(StringUtils.isNotEmpty(maxEmpNum)){ //查询到结果了
            Integer code = Integer.parseInt(maxEmpNum) + 1;
            empNum = code.toString();
            int length = empNum.length();
            for(int i = 6; i > length; i--){
                empNum = "0" + empNum;
            }
        }
        return empNum;
    }

    //根据邮箱查找用户对象--- 要求邮箱号是不能重复的
    private Employee findEmployeeByEmail(String email) {
        return this.baseMapper.selectOne(new QueryWrapper<Employee>().eq("email",email)
                .eq("status",1));
    }
    //基本表单数据校验
    private void checkParam(String empName, String mobile, String email) {
        AssertUtil.isTrue(StringUtils.isBlank(empName),"请输入员工姓名");
        AssertUtil.isTrue(StringUtils.isBlank(mobile),"请输入手机号");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱");
    }


}
