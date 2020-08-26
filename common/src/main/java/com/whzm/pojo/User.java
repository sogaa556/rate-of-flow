package com.whzm.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String id;

    private String username;

    private String phone;

    private String password;

    private Integer type;

    private String status;

    private Integer roleId;

    private String wechat;

    private Integer isDeleted;

}
