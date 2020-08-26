package com.whzm.service;

import com.whzm.pojo.User;
import com.whzm.pojo.vo.LoginEntity;
import com.whzm.util.ResponseEntity;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service.impl
 * @Author: hq
 * @CreateTime: 2020-08-12 10:47
 * @Description:
 */
public interface Loginservice {
    ResponseEntity loginUser(User user) throws Exception;

    ResponseEntity generateCode(User user);

    ResponseEntity<Object> loginByCode(LoginEntity loginEntity);

    ResponseEntity getUserInfo(String userId);
}
