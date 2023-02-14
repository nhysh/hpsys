package com.hp.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

public enum HolidayApplyStatus implements IEnum<Integer>,Serializable {
    SUBMIT("已提交",1),
    APPROVAL("审批中",2),
    REJECTED("已拒绝",3),
    COMPLETED("已完成",4);

    private Integer type;
    private String desc;
    HolidayApplyStatus(String desc,Integer type){
        this.desc = desc;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    @Override
    public Integer getValue() {
        return this.type;
    }
}
