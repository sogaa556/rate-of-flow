package com.whzm.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录参数实体类  手机号+密码  账号+密码  手机+验证码  账号+验证码 邮箱+密码 邮箱+验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {
    private String id;

    private String username;

    private String phone;

    private String password;

    private Integer type;

    private String status;

    private Integer roleId;

    private String wechat;

    private Integer isDeleted;

    private String code; // 手机验证码

}
