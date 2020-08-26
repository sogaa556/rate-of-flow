package com.whzm.mapper;

import com.whzm.pojo.Configuration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.mapper
 * @Author: hq
 * @CreateTime: 2020-08-25 19:24
 * @Description:
 */
@Mapper
public interface ConfigurationMapper {

    String selectByName(@Param("name") String name);

}
