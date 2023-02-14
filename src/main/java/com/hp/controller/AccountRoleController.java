package com.hp.controller;


import com.hp.req.AccountRoleQuery;
import com.hp.service.IAccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 严敏
 * @since 2023-01-04
 */
@Controller
@RequestMapping("/accountRole")
public class AccountRoleController {


    @Autowired
    private IAccountRoleService accountRoleService;
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> accountRoleList(AccountRoleQuery accountRoleQuery){
        return accountRoleService.accountRoleList(accountRoleQuery);
    }
}
