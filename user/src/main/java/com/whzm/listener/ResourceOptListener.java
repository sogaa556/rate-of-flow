package com.whzm.listener;

import com.whzm.pojo.Resource;
import com.whzm.service.RedisService;
import com.whzm.service.ResourceEsService;
import com.whzm.service.ResourceService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.listener
 * @Author: hq
 * @CreateTime: 2020-08-24 17:53
 * @Description:
 */
@Component
public class ResourceOptListener {
    @Autowired
    ResourceEsService resourceEsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private ResourceService resourceService;

    //当mysql进行操作后，会发送一条消息让es进行同步
    @RabbitListener(queuesToDeclare = @Queue("EsAndMysql"))
    public void copyDataToEsFromMysql(String msg) {
        System.out.println(msg);
        //解析消息
        String[] strings = msg.split("-");
        //获取收藏ID
        String resourceId = strings[0];
        //获取操作类型
        String type = strings[1];

        if (type.equals("add")) {
            // 在es业务类中 没有update这个方法
            // 它将 add 和 update 合并了 save
            //取决于你的实体类中 有不有主键
            //从数据据库中  通过ID获取 订单完整数据（后期可以用redis优化）
            Resource resource = (Resource) redisTemplate.opsForValue().get(resourceId);
            System.out.println("resourceName:" + resource.getName());
            resourceEsService.save(resource);
        }
    }

}

