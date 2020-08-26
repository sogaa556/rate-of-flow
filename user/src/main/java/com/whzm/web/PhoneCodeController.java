package com.whzm.web;


import com.whzm.service.Loginservice;
import com.whzm.service.PhoneMsqService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.web
 * @Author: hq
 * @CreateTime: 2020-08-12 10:31
 * @Description: 用户登录
 */
@RestController
@RequestMapping("/user")
public class PhoneCodeController {
    @Autowired
    private Loginservice loginService;
    @Autowired
    private PhoneMsqService phoneMsqService;

    /**
     * 短信验证码
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/sendPhoneMsg/{phoneNum}/{code}")
    public String sendPhoneMsg(@PathVariable("phone") String phone,
                               @PathVariable("code") String code){

        return phoneMsqService.sendPhoneMsg(phone,code);
    }


}

