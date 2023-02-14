package com.hp.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class TreeDto implements Serializable {
    private Integer id;
    private Integer pId;
    private String name;//菜单名
    private Boolean checked;
}
