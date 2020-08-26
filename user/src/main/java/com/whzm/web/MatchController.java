package com.whzm.web;

import com.whzm.service.MatchService;
import com.whzm.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.web
 * @Author: hq
 * @CreateTime: 2020-08-18 11:59
 * @Description:
 */
@RestController
public class MatchController {

    @Autowired
    private MatchService matchService;

    /**
     * 查询用户的所有匹配
     * @param userId
     * @return
     */
    @GetMapping("/selectMatch")
    public ResponseEntity selectMatch(@RequestHeader String userId){
        return matchService.queryMatches(userId);
    }

    /**
     * 模糊查询
     * @param userId
     * @param resourceName1
     * @return
     */
    @GetMapping("/queryByResource/{resourceName1}")
    public ResponseEntity queryByResource(@RequestHeader String userId,@PathVariable String resourceName1){
        String resourceName="%"+resourceName1+"%";
        return matchService.queryByResName(userId,resourceName);
    }

    /**
     * 定时查询最新的十条匹配项
     * @return
     */
    @GetMapping("/queryMatchSelected")
    public ResponseEntity queryMatchSelected(){
        return matchService.queryMatchSelected();
    }
}
