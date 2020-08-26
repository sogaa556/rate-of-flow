package com.whzm.mapper;

import com.whzm.pojo.vo.FlowMatchVo;
import com.whzm.pojo.vo.MatchSelectedVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.mapper
 * @Author: hq
 * @CreateTime: 2020-08-17 20:30
 * @Description:
 */
@Mapper
public interface MatchMapper {

//    @Select("select * from flow_match where (flower_id=#{userId} or advertiser_id=#{userId}) and match_status=1 and is_deleted=0")
    List<FlowMatchVo> selectMatch(@Param("userId") String userId);

//    @Select("select fm.id as id,flow_id as flowId,advertise_id as advertiseId,flower_id as flowerId,advertiser_id as advertiserId,match_time as matchTime\n" +
//            "from resources as r,flow_match as fm,flow_user as fu \n" +
//            "where \n" +
//            "(flower_id = #{userId} or advertiser_id = #{userId})\n" +
//            "and\n" +
//            "(r.id = flow_id or r.id = advertise_id)\n" +
//            "and\n" +
//            "r.user_id = fu.id\n" +
//            "and\n" +
//            "fm.is_deleted = 0 and fm.match_status = 1\n" +
//            "and\n" +
//            "r.name like #{resourceName} ")
    List<FlowMatchVo> selectByUserIdAndResName(@Param("userId")String userId,@Param("resourceName")String resourceName);

//    @Select("select \n" +
//            "fm.advertiser_id aerId,\n" +
//            "fm.flower_id ferId,\n" +
//            "fm.match_time matchTime,\n" +
//            "fu.username userName,\n" +
//            "resources.type type\n" +
//            "from flow_match fm,\n" +
//            "flow_user fu,\n" +
//            "resources\n" +
//            "where\n" +
//            "resources.user_id = fu.id and fm.match_status = 1 and fm.is_deleted=0 \n" +
//            "and\n" +
//            "((fm.advertise_id = resources.id and fm.advertiser_id =  fu.id)\n" +
//            "or\n" +
//            "(fm.flow_id = resources.id and fm.flower_id = fu.id))\n" +
//            "order by match_time desc")
    List<MatchSelectedVo> queryMatchSelected();
}
