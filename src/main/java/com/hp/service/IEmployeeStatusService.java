package com.hp.service;

import com.hp.pojo.EmployeeStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
public interface IEmployeeStatusService extends IService<EmployeeStatus> {

    List<EmployeeStatus> queryAllEmployeeStatus();
}
