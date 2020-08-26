package com.whzm.web;

import com.whzm.service.CollectionService;
import com.whzm.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.web
 * @Author: hq
 * @CreateTime: 2020-08-13 19:36
 * @Description:查询收藏资源
 */
@RestController
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     *
     * @param
     * @return
     * 查询用户所有收藏
     */
    @GetMapping("/queryCollection/{page}/{pageSize}")
    public ResponseEntity queryCollection(@RequestHeader("userId") String userId,@PathVariable String page,@PathVariable String pageSize){
        return collectionService.queryCollection(userId,page,pageSize);
    }

    @GetMapping("/addCollection/{resourceId}")
    public ResponseEntity addCollection(@RequestHeader("userId") String userId,@PathVariable String resourceId){
        return collectionService.addCollection(userId,resourceId);
    }

    @GetMapping("/delCollection/{resourceId}")
    public ResponseEntity delCollection(@RequestHeader("userId") String userId,@PathVariable String resourceId){
        return collectionService.delCollection(userId,resourceId);
    }

    /**
     * 添加收藏(ES)
     * @param userId
     * @param resourceId
     * @return
     */
    @GetMapping("/addEsCollection/{resourceId}")
    public ResponseEntity addEsCollection(@RequestHeader("userId") String userId,@PathVariable String resourceId){
        return collectionService.addEsCollection(userId,resourceId);
    }
}
