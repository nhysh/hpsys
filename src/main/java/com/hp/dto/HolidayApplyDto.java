package com.hp.dto;

import com.hp.pojo.HolidayApply;
import lombok.Data;

@Data
public class HolidayApplyDto extends HolidayApply {
    private String taskId;  //任务id
    private String assignee; //分配人
    private String actName; //任务流程名
}
