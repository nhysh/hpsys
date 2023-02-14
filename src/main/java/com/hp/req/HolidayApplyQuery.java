package com.hp.req;

import lombok.Data;

@Data
public class HolidayApplyQuery extends BaseQuery{
    private String title;
    private String status;
    private String startTime;
    private String endTime;
    private String userId;
}
