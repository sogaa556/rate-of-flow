package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author:
 * @CreateTime: 2020-08-13 12:12
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cooperation implements Serializable {

    /** 主键 */
    private String cooperationId ;
    /** 合作方式名称 */
    private String cooperationName ;
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
