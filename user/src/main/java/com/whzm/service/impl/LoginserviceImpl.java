package com.whzm.service.impl;

import com.whzm.mapper.UserMapper;
import com.whzm.pojo.User;
import com.whzm.pojo.vo.LoginEntity;
import com.whzm.service.Loginservice;
import com.whzm.service.PhoneMsqService;
import com.whzm.service.RedisService;
import com.whzm.util.JwtUtil;
import com.whzm.util.PhoneTest;
import com.whzm.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service.impl
 * @Author: hq
 * @CreateTime: 2020-08-12 10:48
 * @Description:
 */
@Service
@Transactional
@SuppressWarnings("all")
public class LoginserviceImpl implements Loginservice {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PhoneMsqService phoneMsqService;
    @Autowired
    private RedisService redisService;
    @Override
    public ResponseEntity loginUser(User user) {
        if(!PhoneTest.isMobile(user.getPhone())){
            return ResponseEntity.tipsSuccess(400,"手机号不正确");
        }
        User userLogin=userMapper.selectByPhone(user.getPhone());
      if(!StringUtils.isEmpty(userLogin)){
          //User user1=userMapper.selectUser(userLogin.getPhone(),userLogin.getPassword());
          if(user.getPassword().equals(userLogin.getPassword())){
              try {
              return ResponseEntity.entitySuccess(200, "登录成功", JwtUtil.createToken(userLogin));
              }catch (Exception e){
                  return ResponseEntity.tipsSuccess(401,"登陆失败");
              }
              }else{
              return ResponseEntity.tipsSuccess(402, "密码错误");
          }
      }else{
          return ResponseEntity.tipsSuccess(403,"手机号未注册,请注册");
      }
    }

    /**
     * 产生验证码
     * @param user
     * @return
     */
    @Override
    public ResponseEntity generateCode(User user) {
        String phone=user.getPhone();
        if(PhoneTest.isMobile(phone)){
            User userLogin=userMapper.selectByPhone(phone);
            if(userLogin==null){
                return ResponseEntity.tipsSuccess(402,"手机号没注册");
            }
            String code=(new Random().nextInt(89999)+10000)+"";
            String msg=phoneMsqService.sendPhoneMsg(phone,code);
            if(msg.equals("发送成功")){
                return ResponseEntity.tipsSuccess(200,"验证码发送成功");
            }else{
                return ResponseEntity.tipsSuccess(400,"验证码发送失败");
            }
        }else{
            return ResponseEntity.tipsSuccess(401,"手机号不正确");
        }
    }

    /**
     * 验证码登录
     * @param loginEntity
     * @return
     */
    @Override
    public ResponseEntity<Object> loginByCode(LoginEntity loginEntity) {
        String phone=loginEntity.getPhone();
        if(!PhoneTest.isMobile(phone)){
            return ResponseEntity.tipsSuccess(400,"手机号不正确");
        }
        User userLogin=userMapper.selectByPhone(phone);
        if(userLogin!=null){
            String code=loginEntity.getCode();
            //取出redis中存放的code
            String loginCode=(String) redisService.get(phone);
            //输入的验证码redis中存的code
            if(code.equals(loginCode)){
                //通过手机号获取User
                User user=userMapper.selectByPhone(loginEntity.getPhone());
                try {
                    //将user存入token
                    return ResponseEntity.entitySuccess(200, "登录成功", JwtUtil.createToken(user));
                } catch (Exception e) {
                    return ResponseEntity.tipsSuccess(401,"登陆失败");
                }
            }else{
                return ResponseEntity.tipsSuccess(402,"验证码输入错误");
            }
        }else{
            return ResponseEntity.tipsSuccess(403,"手机号没注册");
        }
    }

    /**
     * 得到用户信息
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity getUserInfo(String userId) {
        User user=userMapper.selectById(userId);
        if(StringUtils.isEmpty(user)){
            return ResponseEntity.tipsSuccess(400,"请登录");
        }else{
            return ResponseEntity.tipsSuccess(200,user.getUsername());
        }
    }
}
