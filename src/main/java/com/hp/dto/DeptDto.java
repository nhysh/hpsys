package com.hp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class DeptDto implements Serializable {
    private Integer id;
    private String title;
    private List<DeptDto> children = new ArrayList<>();
}
