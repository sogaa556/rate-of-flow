package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author: 吴严
 * @CreateTime: 2020-08-13 12:14
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Records implements Serializable {

    /** 主键 */
    private Integer id ;
    /** 广告资源信息;关联广告资源表 */
    private String advertisingId ;
    /** 广告发布人;关联用户表 */
    private String advertisingUserId ;
    /** 流量资源信息;关联流量资源表 */
    private String flowId ;
    /** 流量发布人;关联用户表 */
    private String flowUserId ;
    /** 创建人 */
    private String userName ;
    /** 创建时间 */
    private String createTime ;
    /** 更新人 */
    private String updateUser ;
    /** 更新时间 */
    private String updateTime ;
    /** 是否删除;1，已删除；0，正常 */
    private Integer isDeleted ;
}
