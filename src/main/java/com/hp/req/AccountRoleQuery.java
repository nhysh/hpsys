package com.hp.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountRoleQuery extends BaseQuery implements Serializable {
    private Integer roleId;
    private String empName;
    private String empNum;
}
