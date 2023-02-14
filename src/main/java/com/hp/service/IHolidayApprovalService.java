package com.hp.service;

import com.hp.pojo.HolidayApproval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-02-02
 */
public interface IHolidayApprovalService extends IService<HolidayApproval> {

    HolidayApproval queryHolidayApprovalByProcIdAndUserNameAndTaskDefKey(String procInsId, String employeeHolidayHrTaskDefKey, List<String> collect);
}
