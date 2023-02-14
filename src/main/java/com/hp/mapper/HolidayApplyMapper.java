package com.hp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hp.dto.HolidayApplyDto;
import com.hp.pojo.HolidayApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hp.req.MyTaskQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 严敏
 * @since 2023-02-02
 */
public interface HolidayApplyMapper extends BaseMapper<HolidayApply> {
    //查询当前登录人的待办任务
    IPage<HolidayApplyDto> queryMyTask(IPage<HolidayApplyDto> page,@Param("myTaskQuery")  MyTaskQuery myTaskQuery);
}
