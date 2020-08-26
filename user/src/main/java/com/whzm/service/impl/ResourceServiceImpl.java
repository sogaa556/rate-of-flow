package com.whzm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whzm.mapper.ResourcesMapper;
import com.whzm.pojo.Resource;
import com.whzm.pojo.vo.ResourcesListVo;
import com.whzm.service.RedisService;
import com.whzm.service.ResourceEsService;
import com.whzm.service.ResourceService;
import com.whzm.util.ResponseEntity;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service.impl
 * @Author: hq
 * @CreateTime: 2020-08-20 19:21
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ResourceEsService resourceEsService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询置顶服务的前5条(首页)
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity queryTop() {
        List<ResourcesListVo> resourcesListVos=new ArrayList<>();
        //根据购买的置顶等级降序，时间降序(这里数据库查询很耗时，更应该放进redis)
        List<ResourcesListVo> resourcesListVoList=null;
        Boolean exist=redisService.exists("RespurcesTop");
        if(exist){
            String resourcesListVoListStr=(String)redisService.get("RespurcesTop");
            //redis取出来转成list
             resourcesListVoList=JSONObject.parseArray(resourcesListVoListStr,ResourcesListVo.class);
        }else{
             resourcesListVoList= resourcesMapper.queryTop();
            //list转JSON字符串存进redis
            String resourcesListVoListStr=JSON.toJSONString(resourcesListVoList);
            redisService.set("RespurcesTop",resourcesListVoListStr);
        }
       //将时间过期的去掉
        for(int i=0;i<resourcesListVoList.size();i++){
            String aliveTime=resourcesListVoList.get(i).getAliveTime();
            String vcreateTime=resourcesListVoList.get(i).getVcreateTime();
            //当前时间毫秒数
            long nowTime=System.currentTimeMillis();
            //申请时间的毫秒数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date time1 = null;
            try {
                time1 = sdf.parse(vcreateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time2 = time1.getTime();
            //存活时间
            long atime=Long.parseLong(aliveTime);
            if(nowTime-time2 > atime){
                resourcesListVoList.remove(i);
                i--;
            }
        }
       //取出前五条
        if(StringUtils.isEmpty(resourcesListVoList)){
            return  ResponseEntity.tipsSuccess(400,"查询失败");
        }
        if(resourcesListVoList.size()>=5){
            for (int i=0;i<5;i++) {
                resourcesListVos.add(resourcesListVoList.get(i));
            }
        }else{
            for (ResourcesListVo resourcevo:resourcesListVoList) {
                resourcesListVos.add(resourcevo);
            }
        }
        return ResponseEntity.entitySuccess(200,"查询成功",resourcesListVos);
    }

    /**
     * 添加ES资源(Demo)
     * @param
     * @return
     */
    @Override
    public ResponseEntity addEsResource(Resource resource) {
        String id = "H"+ (System.currentTimeMillis() + "" + (int) (Math.random() * 100)).substring(3);
        resource.setId(id);
        //先写入到mysql 成功以后 给mq发送消息
        Integer num=resourcesMapper.addResource(resource);
        if(num>0){
            //存进redis
//            redisService.add(resource.getId(),resource);
            redisTemplate.opsForValue().set(resource.getId(),resource);
            //如果添加成功 我就给mq发送一条消息
            //消息格式 "主键-操作类型" 比如："123123-insert"
            rabbitTemplate.convertAndSend("EsAndMysql",resource.getId()+"-add");
            return ResponseEntity.tipsSuccess(200,"添加成功");
        }
        return ResponseEntity.tipsSuccess(400,"添加失败");
    }



    /**
     * 查询ES资源(Demo)
     * @param searchName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity queryEsResource(String searchName) {
    // 模糊查询 name字段  name中包含字母o的数据
        if(!StringUtils.isEmpty(searchName)){
            WildcardQueryBuilder wqb = QueryBuilders.wildcardQuery("name", "*"+searchName+"*");
            //PageRequest.of(page页数，size每页的数据量)
            Query query = new NativeSearchQueryBuilder().withQuery(wqb).withPageable(PageRequest.of(0, 2)).build();
            Page<Resource> resourcePage = resourceEsService.search(query);
            return ResponseEntity.entitySuccess(200,"ok",resourcePage);
        }
        return ResponseEntity.tipsSuccess(400,"查询失败");
    }

    /**
     * 根据id查询资源
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Resource selectById(String resourceId){
        return resourcesMapper.selectById(resourceId);
    }
}
