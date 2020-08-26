package com.whzm.service.impl;

import com.whzm.mapper.CollectionMapper;
import com.whzm.mapper.ResourcesMapper;
import com.whzm.mapper.UserMapper;
import com.whzm.pojo.Collection;
import com.whzm.pojo.User;
import com.whzm.pojo.vo.ResourcesListVo;
import com.whzm.service.ResourceEsService;
import com.whzm.service.CollectionService;
import com.whzm.service.RedisService;
import com.whzm.util.ResponseEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
 * @CreateTime: 2020-08-13 20:27
 * @Description:
 */
@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ResourcesMapper resourceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ResourceEsService collectionEsService;

    /**
     * @param userId
     * @param page
     * @param pageSize
     * @return
     * 分页查询当前用户的所有收藏
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity queryCollection(String userId,String page, String pageSize) {
        List<ResourcesListVo>resourcesListVos=new ArrayList<>();
        List<Collection>collections= collectionMapper.selectByuserId(userId);
        //查询出用户对应的所有资源信息
        for (Collection coll:collections) {
           ResourcesListVo resourcesListVo=resourceMapper.queryAll(userId,coll.getResourceId());
            if(StringUtils.isEmpty(resourcesListVo)){
                continue;
            }
            String resUserId=resourcesListVo.getUserId();
            //先看缓存中有没有
            Boolean exist=redisService.exists(resUserId);
            //缓存中有的话直接取
            User user=null;
            if(exist){
                user=(User) redisService.get(resUserId);
            }else{
                //从数据库中取
                user=userMapper.selectById(resUserId);
                //存进redis
                redisService.set(resUserId,user);
            }
            resourcesListVo.setUserName(user.getUsername());
            resourcesListVos.add(resourcesListVo);
        }
        //分页
        Integer count=resourcesListVos.size();
        Integer pageNum=Integer.parseInt(page);
        Integer limit=Integer.parseInt(pageSize);
        Integer start=(pageNum-1)*limit;
        Integer end=(pageNum-1)*limit+limit;
        if(end>count){
            end=count;
        }
        List<ResourcesListVo>list=new ArrayList<>();
        list=resourcesListVos.subList(start,end);

        if(resourcesListVos.size()>0){
            return ResponseEntity.pageSuccess(200,"查询成功",list,count);
        }else{
            return ResponseEntity.tipsSuccess(400,"查询失败");
        }
    }

    /**
     * 用户添加收藏
     * @param userId
     * @param resourceId
     * @return
     */
    @Override
    public ResponseEntity addCollection(String userId, String resourceId) {
        String id = "H"+ (System.currentTimeMillis() + "" + (int) (Math.random() * 100)).substring(3);
        Integer idEffective=0;
        //先添加进数据库
        Integer num=collectionMapper.addCollection(id,userId,resourceId,idEffective);
        if(num>0){
            //此处添加进redis
            redisService.set(id,collectionMapper.selectById(id));
            return ResponseEntity.tipsSuccess(200,"添加成功");
        }
        return ResponseEntity.tipsSuccess(400,"添加失败");
    }

    /**
     * 用户删除该条收藏
     * @param userId
     * @param resourceId
     * @return
     */
    @Override
    public ResponseEntity delCollection(String userId, String resourceId) {
        Collection collection=collectionMapper.selectByResIdAndUserId(userId,resourceId);
        //将数据值设置为空
        redisService.set(collection.getId(),null,5L);
        //删数据库
        Integer num=collectionMapper.delCollectionById(userId,resourceId);
        if(num>0){
            redisService.set(collection.getId(),null,5L);
            return ResponseEntity.tipsSuccess(200,"删除成功");
        }else{
            return ResponseEntity.tipsSuccess(400,"删除失败");
        }
    }

    /**
     * 添加收藏(ES)
     * @param userId
     * @param resourceId
     * @return
     */
    @Override
    public ResponseEntity addEsCollection(String userId, String resourceId) {
        //生成id
        String id = "H"+ (System.currentTimeMillis() + "" + (int) (Math.random() * 100)).substring(3);
        //可添加、初始默认为有效
        Integer idEffective=0;
        Collection collection=new Collection(id,userId,resourceId,idEffective);
        //先写入到mysql 成功以后 给mq发送消息
        Integer num=collectionMapper.addCollection(id,userId,resourceId,idEffective);
        if(num>0){
            //存进redis
            redisService.add(id,collection);
            //如果添加成功 我就给mq发送一条消息
            //消息格式 "主键-操作类型" 比如："123123-insert"
            rabbitTemplate.convertAndSend("EsAndMysql",id+"-add");
            return ResponseEntity.tipsSuccess(200,"添加成功");
        }
        return ResponseEntity.tipsSuccess(400,"添加失败");
    }

    @Override
    public Collection getCollectionById(String collectionId) {
        return collectionMapper.selectBycollectionId(collectionId);
    }

    @Override
    public ResponseEntity queryEsCollection(String userId, String SearchName, String page, String pageSize) {
        return null;
    }

    /**
     * 查询ES中的数据
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
//    @Override
//    public ResponseEntity queryEsCollection(String userId,String SearchName, String page, String pageSize) {
//        List<ResourcesListVo>resourcesListVos=new ArrayList<>();
//        //去ES取出所有的收藏
//        Iterable<Collection> collections= collectionEsService.findAll();
//        for (Collection coll:collections) {
//            ResourcesListVo resourcesListVo=resourceMapper.queryAll(userId,coll.getResourceId());
//            if(StringUtils.isEmpty(resourcesListVo)){
//                continue;
//            }
//            User user=userMapper.selectById(resourcesListVo.getUserId());
//            resourcesListVo.setUserName(user.getUsername());
//            resourcesListVos.add(resourcesListVo);
//        }
//        return ResponseEntity.entitySuccess(200,"查询成功",resourcesListVos);
//    }
}
