package com.whzm.service;

import com.whzm.pojo.Resource;
import com.whzm.util.ResponseEntity;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service
 * @Author: hq
 * @CreateTime: 2020-08-20 19:21
 * @Description:
 */
public interface ResourceService {
    public ResponseEntity queryTop();

    ResponseEntity queryEsResource(String searchName);

    ResponseEntity addEsResource(Resource resource);

    Resource selectById(String resourceId);
}
