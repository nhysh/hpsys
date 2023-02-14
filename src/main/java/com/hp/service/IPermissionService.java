package com.hp.service;

import com.hp.pojo.Permission;
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
public interface IPermissionService extends IService<Permission> {

    List<Integer> queryRoleHasAllMenuIdsByRoleId(Integer roleId);

    List<String> findAuthorityByUserName(String userName);//根据用户名查询拥有的权限值
}
