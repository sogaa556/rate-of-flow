package com.whzm.web;

import com.whzm.pojo.User;
import com.whzm.pojo.vo.LoginEntity;
import com.whzm.service.Loginservice;
import com.whzm.util.ResponseEntity;
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
public class LoginController {

    @Autowired
    private Loginservice loginService;

    /**
     * 用户名和密码登录
     * @param user
     * @return
     */
        @PostMapping("/user/loginWithUsernameAndPass")
    public ResponseEntity loginWithUsernameAndPass(@RequestBody User user){
        try {
            return loginService.loginUser(user);
        } catch (Exception e) {
            return ResponseEntity.tipsSuccess(404,"登录失败");
        }
    }

    /**
     * 验证码生成
     */
    @PostMapping("/user/generateCode")
    public ResponseEntity generateCode(@RequestBody User user){
        return loginService.generateCode(user);
    }

    /**
     * 短信验证码登录
     * @param
     * @param
     * @return
     */
    @PostMapping("/user/loginByCode")
    public ResponseEntity<Object> loginByCode(@RequestBody LoginEntity loginEntity){
      return  loginService.loginByCode(loginEntity);
    }

    /**
     *获取用户信息
     * @param
     * @return
     */
    @PostMapping("/user/get_user_info")
    public ResponseEntity getUserInfo(@RequestHeader("userId") String userId){
        return loginService.getUserInfo(userId);
    }
 }
