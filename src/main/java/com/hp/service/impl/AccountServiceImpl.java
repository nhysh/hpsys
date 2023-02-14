package com.hp.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hp.pojo.Account;
import com.hp.mapper.AccountMapper;
import com.hp.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.utils.AssertUtil;
import com.hp.utils.SpringSecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper,Account> implements IAccountService {

    @Override
    public Account findAccountByUserName(String userName) {
        return this.baseMapper
                .selectOne(new QueryWrapper<Account>().eq("user_name",userName));

    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void updatePassword(String oldPassword, String newPassword, String repeatPassword) {
        //先做校验
        //1.判断传入的密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(repeatPassword),"重复密码不能为空");
        //2.原始密码输入正确性
        //获取到当前登录用户名的密码
        //当前登录的用户名
        String name = SpringSecurityUtils.getAccountName();
        //根据name查询对象
        Account account = this.findAccountByUserName(name);
        //判断原始密码是否正确
        AssertUtil.isTrue(!(passwordEncoder.matches(oldPassword,account.getPassword())),"原始密码不正确");
        //判断原始密码是否与新密码一致
        AssertUtil.isTrue(passwordEncoder.matches(newPassword,account.getPassword()),"原始密码和新密码不能一样");
        //新密码和重复密码药一致
        AssertUtil.isTrue(!(newPassword.equals(repeatPassword)),"两次输入不一致");
        //如果没问题就更新密码
        account.setPassword(passwordEncoder.encode(newPassword));
        AssertUtil.isTrue(this.baseMapper.updateById(account)==0,"修改密码失败");
    }
}
