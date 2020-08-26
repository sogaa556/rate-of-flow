package com.whzm.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo.vo
 * @Author: hq
 * @CreateTime: 2020-08-17 19:50
 * @Description: 匹配资源VO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowMatchVo {
    private String id;              //匹配资源表id(可在页面上展示为流水编号)

    private String flowId;          //流量主资源id
    private String advertiseId;     //广告主资源id

    private Integer matchStatus;    //匹配状态( 0、未匹配1、匹配成功2、匹配失败）

    private String advertiserId;    //广告主id
    private String flowerId;        //流量主id

    private String advertiserName; //发布广告资源的用户姓名
    private String flowerName;  //发布流量资源的用户姓名

    private String advertiserWechat; //发布广告资源的用户微信
    private String flowerWechat;  //发布流量资源的用户微信

    private String flowCompany;   //流量主公司
    private String advertiseCompany;   //广告主公司


    private String flowName;    //流量资源名称
    private String advertiseName;//广告资源名称

    private String matchTime;         //匹配时间
}
