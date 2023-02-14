package com.hp.req;

import lombok.Data;

@Data
public class MyTaskQuery extends BaseQuery{
    private String userName;
    private String processDefinitionKey;
}
