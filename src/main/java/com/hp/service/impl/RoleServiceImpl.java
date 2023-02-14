package com.hp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hp.pojo.AccountRole;
import com.hp.pojo.Menu;
import com.hp.pojo.Permission;
import com.hp.pojo.Role;
import com.hp.mapper.RoleMapper;
import com.hp.resp.RespBean;
import com.hp.service.IAccountRoleService;
import com.hp.service.IMenuService;
import com.hp.service.IPermissionService;
import com.hp.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.utils.AssertUtil;
import com.hp.utils.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public Map<String, Object> findRoles() {
        List<Role> roles = this.baseMapper.selectList(new QueryWrapper<Role>());
        return PageResultUtil.getResult((long)roles.size(),roles);
    }

    @Override
    public RespBean saveRole(Role role  ) {
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        this.baseMapper.insert(role);
        return RespBean.success("数据添加成功");
    }

    @Override
    public RespBean updateRole(Role role) {
        this.baseMapper.updateById(role);
        return RespBean.success("数据修改成功");
    }

    @Autowired
    private IAccountRoleService accountRoleService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void cancelRoleToUser(Integer roleId, Integer accountId) {
        Role role = this.getOne(new QueryWrapper<Role>().eq("id", roleId));
        AssertUtil.isTrue(null == role,"待取消的角色记录不存在");
        AssertUtil.isTrue(role.getStatus().equals("0")  || role.getStatus().equals("2"),"角色删除");
        AssertUtil.isTrue(!accountRoleService.remove(
                new QueryWrapper<AccountRole>()
                        .eq("role_id",roleId)
                        .eq("account_id",accountId))
                ,"取消分配失败!");
    }


    @Autowired
    private IPermissionService permissionService;
    @Autowired IMenuService menuService;
    @Override
    public void addGrant(Integer[] mids, Integer roleId) {
        Role temp = this.getById(roleId); //查询要添加权限的角色对象
        AssertUtil.isTrue(null == roleId || null == temp,"待授权的角色不存在!");
        //准备判断该角色是否有原始权限
        int count = this.permissionService.count(new QueryWrapper<Permission>().eq("role_id", roleId));
        if(count > 0){
            //删除原始权限
            AssertUtil.isTrue(!(this.permissionService.remove(new QueryWrapper<Permission>()
                    .eq("role_id",roleId))),"权限分配失败");
        }
        if(null != mids && mids.length > 0){
            List<Permission> permissions = new ArrayList<>();
            for (Integer mid : mids) {
                Permission permission = new Permission();
                permission.setMenuId(mid);//菜单id
                permission.setRoleId(roleId);//角色id
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                //权限值--- 用数字代表权限
                permission.setAclValue(this.menuService.getOne(new QueryWrapper<Menu>().eq("id",mid)).getOptValue());
                permissions.add(permission);//添加到集合中
            }
            //添加
            AssertUtil.isTrue(!(this.permissionService.saveBatch(permissions)),"角色授权失败");
        }
    }

}
