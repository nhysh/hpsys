package com.hp.mapper;

import com.hp.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    //根据角色id，查询该角色所拥有的菜单id集合
    List<Integer> queryRoleHasAllMenuIdsByRoleId(Integer roleId);

    List<String> findAuthorityByUserName(String userName);//根据用户名查询拥有的权限值
}
