package com.whzm.service.impl;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.whzm.mapper.ConfigurationMapper;
import com.whzm.service.RedisService;
import com.whzm.service.PhoneMsqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @BelongsProject: springcloud-demo2
 * @BelongsPackage: com.example.message1.service.impl
 * @Author: hq
 * @CreateTime: 2020-07-29 15:24
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class PhoneMsqServiceImpl implements PhoneMsqService {

    @Autowired
    private RedisService redisService;
    @Autowired
    ConfigurationMapper configurationMapper;
    /**
     * 发送短信验证码
     * @param phone
     * @param code
     * @return
     */
    @Override
    public String sendPhoneMsg(String phone,String code) {

        String accessKeyId=configurationMapper.selectByName("regionId");
        String secret=configurationMapper.selectByName("secret");
        String SignName=configurationMapper.selectByName("SignName");
        String TemplateCode=configurationMapper.selectByName("TemplateCode");

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);

        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            redisService.set(phone,code,(long)3*60);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return "发送成功";
    }
}
