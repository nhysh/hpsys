package com.hp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hp.dto.AccountRoleDto;
import com.hp.pojo.AccountRole;
import com.hp.mapper.AccountRoleMapper;
import com.hp.req.AccountRoleQuery;
import com.hp.resp.RespBean;
import com.hp.service.IAccountRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.utils.PageResultUtil;
import org.springframework.stereotype.Service;

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
public class AccountRoleServiceImpl extends ServiceImpl<AccountRoleMapper, AccountRole> implements IAccountRoleService {

    @Override
    public RespBean saveAccountRole(Integer roleId, Integer eId) {
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(eId);
        accountRole.setRoleId(roleId);
        this.baseMapper.insert(accountRole);
        return RespBean.success("记录添加成功");
    }

    @Override
    public Map<String, Object> accountRoleList(AccountRoleQuery accountRoleQuery) {
        IPage<AccountRoleDto> page =new Page<>(accountRoleQuery.getPage(),accountRoleQuery.getLimit());
        page=this.baseMapper.accountRoleList(page,accountRoleQuery);
        return PageResultUtil.getResult(page.getTotal(),page.getRecords());
    }
}
