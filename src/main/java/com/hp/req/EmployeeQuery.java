package com.hp.req;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployeeQuery extends BaseQuery implements Serializable {
    private String empName;
    private Integer deptId;
    private String empNum;
}
