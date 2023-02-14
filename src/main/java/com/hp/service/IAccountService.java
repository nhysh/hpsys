package com.hp.service;

import com.hp.pojo.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
public interface IAccountService extends IService<Account> {
    //根据用户名查询账户
    public Account findAccountByUserName(String userName);

    public void updatePassword(String oldPassword, String newPassword, String repeatPassword);
}
