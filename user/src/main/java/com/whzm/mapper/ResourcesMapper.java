package com.whzm.mapper;

import com.whzm.pojo.Resource;
import com.whzm.pojo.vo.ResourcesListVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.mapper
 * @Author: hq
 * @CreateTime: 2020-08-13 20:48
 * @Description:
 */
@Mapper
public interface ResourcesMapper {
    //@Select("select * from resources where id=#{id}")
    Resource selectById(@Param("id")String id);

    /**
     * 查询所有
     * @param userId
     * @param resourceId
     * @return
     */
    ResourcesListVo queryAll(@RequestParam("userId") String userId, @RequestParam("resourceId")String resourceId);

    //@Select("select * from resources where name like #{resourceName}")
    Resource queryByResName(@Param("resourceName") String resourceName);

    /**
     * 首页置顶
     */
    List<ResourcesListVo> queryTop();

    /**
     * 增加资源
     * @param resource
     * @return
     */
    //@Insert("insert into resources(id,name) values(#{id},#{name})")
    Integer addResource(Resource resource);
}
