package com.whzm.service;

import com.whzm.util.ResponseEntity;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service
 * @Author: hq
 * @CreateTime: 2020-08-17 20:35
 * @Description:
 */
public interface MatchService {

    ResponseEntity queryMatches(String userId);

    ResponseEntity queryByResName(String userId,String resourceName);

    ResponseEntity queryMatchSelected();
}
