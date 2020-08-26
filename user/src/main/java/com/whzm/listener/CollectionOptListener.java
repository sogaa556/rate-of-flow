package com.whzm.listener;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.listener
 * @Author: hq
 * @CreateTime: 2020-08-24 16:38
 * @Description:
 */
public class CollectionOptListener {
//    @Autowired
//    CollectionEsService collectionEsService;
//
//    @Autowired
//    private CollectionService collectionService;
//
//    //当mysql进行操作后，会发送一条消息让es进行同步
//    @RabbitListener(queuesToDeclare = @Queue("EsAndMysql"))
//    public void copyDataToEsFromMysql(String msg){
//        //解析消息
//        String[] strings = msg.split("-");
//        //获取收藏ID
//        String collectionId = strings[0];
//        //获取操作类型
//        String type = strings[1];
//
//        if(type.equals("add")){
//            // 在es业务类中 没有update这个方法
//            // 它将 add 和 update 合并了 save
//            //取决于你的实体类中 有不有主键
//            //从数据据库中  通过ID获取 订单完整数据（后期可以用redis优化）
//            Collection collection = collectionService.getCollectionById(collectionId);
//            collectionEsService.save(collection);
//        }
//    }
}
