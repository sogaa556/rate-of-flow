package com.whzm.mapper;

import com.whzm.pojo.Collection;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.mapper
 * @Author: hq
 * @CreateTime: 2020-08-13 19:41
 * @Description:
 */
@Mapper
public interface CollectionMapper {

    //@Select("select * from flow_collection where user_id= #{id}")
    List<Collection> selectByuserId(@Param("id") String id);

   // @Insert("insert into flow_collection(id,user_id,resource_id,is_effective)values(#{id},#{userId},#{resourceId},#{isEffective})")
    Integer  addCollection(@Param("id")String id,@Param("userId")String userId,@Param("resourceId")String resourceId,@Param("isEffective")Integer isEffective);

    //@Update("update flow_collection set is_deleted=1 where user_id=#{userId} and resource_id=#{resourceId}")
    Integer delCollectionById(@Param("userId")String userId, @Param("resourceId")String resourceId);

   // @Select("select * from flow_collection where id=#{collectionId}")
    Collection selectBycollectionId(@Param("collectionId")String collectionId);

    Collection selectById(@Param("id") String id);

    Collection selectByResIdAndUserId(String userId, String resourceId);
}
