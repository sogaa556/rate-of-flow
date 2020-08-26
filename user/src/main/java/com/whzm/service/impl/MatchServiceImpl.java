package com.whzm.service.impl;

import com.whzm.mapper.MatchMapper;
import com.whzm.mapper.ResourcesMapper;
import com.whzm.mapper.UserMapper;
import com.whzm.pojo.Resource;
import com.whzm.pojo.User;
import com.whzm.pojo.vo.FlowMatchVo;
import com.whzm.pojo.vo.MatchSelectedVo;
import com.whzm.service.MatchService;
import com.whzm.service.RedisService;
import com.whzm.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service.impl
 * @Author: hq
 * @CreateTime: 2020-08-17 20:35
 * @Description:
 */
@Service
@Transactional
@SuppressWarnings("all")
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private ResourcesMapper resourcesMapperer;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 查询当前用户已匹配流量
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity queryMatches(String userId) {
        List<FlowMatchVo> flowMatches=matchMapper.selectMatch(userId);
        //往实体类里面添加对应的展示信息
        if(StringUtils.isEmpty(flowMatches)||flowMatches.size()==0){
        return ResponseEntity.tipsSuccess(400,"无匹配资源");
        }
        for (FlowMatchVo flo:flowMatches) {
            Resource advertiseResource=null;
            //获取匹配的广告主的资源信息
            Boolean exist=redisService.exists(flo.getAdvertiseId());
            if(exist){
                 advertiseResource=(Resource)redisService.get(flo.getAdvertiseId());
            }else{
                advertiseResource=resourcesMapperer.selectById(flo.getAdvertiseId());
                redisService.set(flo.getAdvertiseId(),advertiseResource);
            }
           //发布资源的用户信息
            User advertiser=null;
            Boolean exist1=redisService.exists(flo.getAdvertiserId());
            if(exist1){
                advertiser=(User)redisService.get(flo.getAdvertiserId());
            }else{
                 advertiser= userMapper.selectById(flo.getAdvertiserId());
                redisService.set(flo.getAdvertiserId(),advertiser);
            }
            //发布资源的用户姓名
            flo.setAdvertiserName(advertiser.getUsername());
            //发布资源的用户微信
            flo.setAdvertiserWechat(advertiser.getWechat());
            //广告主的公司
            flo.setAdvertiseCompany(advertiseResource.getCompany());
            //广告资源名称
            flo.setAdvertiseName(advertiseResource.getName());

            //获取匹配的流量主的资源信息
            Resource flowResource=null;
            Boolean exist2=redisService.exists(flo.getFlowId());
            if(exist2){
                flowResource=(Resource)redisService.get(flo.getFlowId());
            }else{
                 flowResource=resourcesMapperer.selectById(flo.getFlowId());
                redisService.set(flo.getFlowId(),flowResource);
            }
            //获取资源的用户信息
            User flower=null;
            Boolean exist3=redisService.exists(flo.getFlowerId());
            if(exist3){
                flower=(User)redisService.get(flo.getFlowerId());
            }else{
                 flower= userMapper.selectById(flo.getFlowerId());
                redisService.set(flo.getFlowerId(),flower);
            }
            //获取资源的用户姓名
            flo.setFlowerName(flower.getUsername());
            //获取资源的用户微信
            flo.setAdvertiserWechat(flower.getWechat());
           //流量主的公司
            flo.setFlowCompany(flowResource.getCompany());
            //流量资源名称
            flo.setFlowName(flowResource.getName());
        }
        return ResponseEntity.entitySuccess(200,"查询成功",flowMatches);
    }

    /**
     * 在当前用户已匹配表中根据流量名称模糊查询
     * @param userId
     * @param resourceName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity queryByResName(String userId,String resourceName) {
        List<FlowMatchVo> flowMatches=matchMapper.selectByUserIdAndResName(userId,resourceName);
        if(StringUtils.isEmpty(flowMatches)||flowMatches.size()==0){
            return ResponseEntity.tipsSuccess(400,"无匹配资源");
        }
        for (FlowMatchVo flo:flowMatches) {
            //获取匹配的广告主的资源信息
            Resource advertiseResource=resourcesMapperer.selectById(flo.getAdvertiseId());
            //发布资源的用户信息
            User advertiser= userMapper.selectById(flo.getAdvertiserId());
            //发布资源的用户姓名
            flo.setAdvertiserName(advertiser.getUsername());
            //发布资源的用户微信
            flo.setAdvertiserWechat(advertiser.getWechat());
            //广告主的公司
            flo.setAdvertiseCompany(advertiseResource.getCompany());
            //广告资源名称
            flo.setAdvertiseName(advertiseResource.getName());

            //获取匹配的流量主的资源信息
            Resource flowResource=resourcesMapperer.selectById(flo.getFlowId());
            //获取资源的用户信息
            User flower= userMapper.selectById(flo.getFlowerId());
            //获取资源的用户姓名
            flo.setFlowerName(flower.getUsername());
            //获取资源的用户微信
            flo.setAdvertiserWechat(flower.getWechat());
            //流量主的公司
            flo.setFlowCompany(flowResource.getCompany());
            //流量资源名称
            flo.setFlowName(flowResource.getName());
        }
        return ResponseEntity.entitySuccess(200,"查询成功",flowMatches);
    }

    /**
     * 定时查询最新的十条匹配项(首页轮播)
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity queryMatchSelected() {
        List<MatchSelectedVo>matchSelectedVoList=matchMapper.queryMatchSelected();
        List<MatchSelectedVo>matchSelectedVo2=new ArrayList<>();
        List<MatchSelectedVo>matchSelectedVo=new ArrayList<>();
        //两条数据合并为一条
        while(true){
            if(matchSelectedVoList!=null&&matchSelectedVoList.size()!=0){
                if(matchSelectedVoList.get(0).getAerId().equals(matchSelectedVoList.get(1).getAerId())&& matchSelectedVoList.get(0).getFerId().equals(matchSelectedVoList.get(1).getFerId())){
                    if(matchSelectedVoList.get(0).getType()==1){
                        matchSelectedVoList.get(0).setFerName(matchSelectedVoList.get(0).getUserName());
                        matchSelectedVoList.get(0).setAreName(matchSelectedVoList.get(1).getUserName());
                    }else{
                        matchSelectedVoList.get(0).setAreName(matchSelectedVoList.get(0).getUserName());
                        matchSelectedVoList.get(0).setFerName(matchSelectedVoList.get(1).getUserName());
                    }
                    matchSelectedVo2.add(matchSelectedVoList.get(0));
                    //移除前两条数据
                    matchSelectedVoList.remove(0);
                    matchSelectedVoList.remove(0);
                }else{
                    break;
                }
            }else{
                break;
            }
        }

        if(matchSelectedVo2.size()>10){
            for(int i=0;i<10;i++){
                matchSelectedVo.add(matchSelectedVo2.get(i));
            }
        }else{
            for (MatchSelectedVo matchSe:matchSelectedVo2) {
                matchSelectedVo.add(matchSe);
            }
        }
        return ResponseEntity.entitySuccess(200,"ok",matchSelectedVo);
    }
}
