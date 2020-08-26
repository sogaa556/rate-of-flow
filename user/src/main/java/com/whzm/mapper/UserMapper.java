package com.whzm.mapper;


import com.whzm.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.mapper
 * @Author: hq
 * @CreateTime: 2020-08-12 10:49
 * @Description:
 */
@Mapper
public interface UserMapper {

   // @Select("select * from flow_user where phone=#{phone} and password=#{password}")
    User selectUser(@Param("phone") String phone, @Param("password")String password);

    //@Select("select * from flow_user where phone=#{phone}")
    User selectByPhone(@Param("phone")String phone);

    //@Select("select * from flow_user where id=#{userId}")
    User selectById(@Param("userId")String userId);
}
