package com.hp.req;

import lombok.Data;

import java.io.Serializable;
@Data
public class BaseQuery implements Serializable {
    private Integer page;//当前页码
    private Integer limit;//每页显示的信息条数


}
