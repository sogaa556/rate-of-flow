package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm
 * @Author: hq
 * @CreateTime: 2020-08-25 19:22
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {
    private String id;
    private String name;
    private String value;
}
