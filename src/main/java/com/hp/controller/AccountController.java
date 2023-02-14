package com.hp.controller;


import com.hp.resp.RespBean;
import com.hp.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;
    //进入到修改密码界面
    @GetMapping("/toPasswordPage")
    public String toPasswordPage(){
        return "account/password";
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public RespBean updateUserPassword(String oldPassword,
                                       String newPassword,
                                       String repeatPassword){
//        try {
//            accountService.updatePassword(oldPassword,newPassword,repeatPassword);
//            return RespBean.success("密码修改成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return RespBean.error(e.getMessage());
//        }
        accountService.updatePassword(oldPassword,newPassword,repeatPassword);
        return RespBean.success("密码修改成功");
    }
}
