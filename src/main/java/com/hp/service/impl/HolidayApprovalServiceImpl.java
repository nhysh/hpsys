package com.hp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hp.pojo.HolidayApproval;
import com.hp.mapper.HolidayApprovalMapper;
import com.hp.service.IHolidayApprovalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 严敏
 * @since 2023-02-02
 */
@Service
public class HolidayApprovalServiceImpl extends ServiceImpl<HolidayApprovalMapper, HolidayApproval> implements IHolidayApprovalService {

    @Override
    public HolidayApproval queryHolidayApprovalByProcIdAndUserNameAndTaskDefKey(String procInsId, String taskDefKey, List<String> collect) {
        QueryWrapper<HolidayApproval> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_instance_id",procInsId);
        queryWrapper.eq("task_def_key",taskDefKey);
        collect.forEach(emp->{
            queryWrapper.in("user_name",emp);
        });
        HolidayApproval holidayApproval = this.baseMapper.selectOne(queryWrapper);
        return holidayApproval;
    }
}
