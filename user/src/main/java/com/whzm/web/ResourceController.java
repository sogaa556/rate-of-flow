package com.whzm.web;

import com.whzm.pojo.Resource;
import com.whzm.service.ResourceService;
import com.whzm.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.web
 * @Author: hq
 * @CreateTime: 2020-08-20 19:29
 * @Description:
 */
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 查询置顶
     * @return
     */
    @PostMapping("/queryTop")
    public ResponseEntity queryTop(){
        return resourceService.queryTop();
    }

    /**
     * 添加资源
     * @return
     */
    @PostMapping("/addEsResource")
    public ResponseEntity addEsResource(@RequestBody Resource resource){
        return resourceService.addEsResource(resource);
    }

    /**
     * 查询资源
     * @return
     */
    @PostMapping("/queryResource")
    public ResponseEntity queryResource(@RequestBody Resource resource){

        return resourceService.queryEsResource(resource.getName());
    }
}
