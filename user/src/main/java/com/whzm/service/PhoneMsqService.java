package com.whzm.service;

/**
 * @BelongsProject: springcloud-demo2
 * @BelongsPackage: com.example.message1.service.impl
 * @Author: hq
 * @CreateTime: 2020-07-29 15:23
 * @Description:
 */
public interface PhoneMsqService {

    public String sendPhoneMsg(String phoneNum,String code);
}
