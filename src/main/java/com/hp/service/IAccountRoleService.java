package com.hp.service;

import com.hp.pojo.AccountRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hp.req.AccountRoleQuery;
import com.hp.resp.RespBean;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
public interface IAccountRoleService extends IService<AccountRole> {

    RespBean saveAccountRole(Integer roleId, Integer eId);

    Map<String, Object> accountRoleList(AccountRoleQuery accountRoleQuery);
}
