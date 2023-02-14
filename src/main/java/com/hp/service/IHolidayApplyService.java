package com.hp.service;

import com.hp.pojo.HolidayApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hp.req.HolidayApplyQuery;
import com.hp.req.MyTaskQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-02-02
 */
public interface IHolidayApplyService extends IService<HolidayApply> {
    //添加请假单记录
    public void saveHolidayApply(HolidayApply holidayApply);


    //查询当事人请假单
    Map<String, Object> queryMyHolidayApply(HolidayApplyQuery holidayApplyQuery);

    void deleteHolidayApply(Integer id);

    Map<String, Object> queryMyTaskList(MyTaskQuery myTaskQuery);
}
