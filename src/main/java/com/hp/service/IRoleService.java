package com.hp.service;

import com.hp.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IRoleService extends IService<Role> {

    Map<String, Object> findRoles();

    RespBean saveRole(Role role );

    RespBean updateRole(Role role);

    void cancelRoleToUser(Integer roleId, Integer accountId);

    void addGrant(Integer[] mids, Integer roleId);//授权
}
