package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author: 吴严
 * @CreateTime: 2020-08-13 12:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area implements Serializable {
    /**
     * 主键
     */
    private String areaId;
    /**
     * 详细地址;关联地址表
     */
    private Integer addressId;
    /**
     * 详细地址
     */
    private String addressDetail;
    /**
     * 创建人
     */
    private String userName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否删除;1，已删除；0，正常
     */
    private Integer isDeleted;
}
