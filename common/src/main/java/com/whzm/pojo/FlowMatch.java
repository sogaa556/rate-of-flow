package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author: hq
 * @CreateTime: 2020-08-19 15:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowMatch {
    private String id;              //匹配资源表id(可在页面上展示为流水编号)

    private String flowId;          //流量主资源id
    private String advertiseId;     //广告主资源id

    private Integer matchStatus;    //匹配状态( 0、未匹配1、匹配成功2、匹配失败）

    private String advertiserId;    //广告主id
    private String flowerId;        //流量主id

    private  String matchTime;
    private Integer isDeleted;
}
