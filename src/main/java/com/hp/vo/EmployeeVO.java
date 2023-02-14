package com.hp.vo;

import com.hp.pojo.Employee;
import lombok.Data;

import java.io.Serializable;
@Data
public class EmployeeVO extends Employee implements Serializable {
    private String userName;
    private String deptName;
    private String titleName;
    private String empStatus;
}
