package com.hp.dto;

import com.hp.utils.PageResultUtil;
import lombok.Data;

import java.io.Serializable;
@Data
public class AccountRoleDto implements Serializable {
    private Integer aid;
    private String userName;
    private String empNum;
    private String empName;
    private String  deptNum;
    private String title;
}
