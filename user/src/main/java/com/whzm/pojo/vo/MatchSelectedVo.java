package com.whzm.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo.vo
 * @Author: hq
 * @CreateTime: 2020-08-21 10:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchSelectedVo {
    private String aerId;
    private String ferId;
    private String matchTime;
    private String userName;
    private Integer type;
    private String areName;
    private String ferName;
}
