package com.whzm.service;

import com.whzm.pojo.Collection;
import com.whzm.util.ResponseEntity;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service
 * @Author: hq
 * @CreateTime: 2020-08-13 20:26
 * @Description:
 */
public interface CollectionService {

    ResponseEntity queryCollection(String id,String page,String pageSize);

    ResponseEntity addCollection(String userId, String resourceId);

    ResponseEntity delCollection(String userId, String resourceId);

    ResponseEntity addEsCollection(String userId, String resourceId);

    Collection getCollectionById(String collectionId);

    ResponseEntity queryEsCollection(String userId,String SearchName, String page, String pageSize);
}
