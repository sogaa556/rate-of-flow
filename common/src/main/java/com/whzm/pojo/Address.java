package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author: 吴严
 * @CreateTime: 2020-08-13 12:15
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    /**
     * 主键
     */

    private Integer id;
    /**
     * 地区名称
     */
    private String address;
    /**
     * 父类id
     */
    private Integer parentId;
}
