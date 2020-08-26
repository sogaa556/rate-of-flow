package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author: hq
 * @CreateTime: 2020-08-13 19:33
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection implements Serializable {

    private String id;

    private String userId;

    private String resourceId;

    private Integer isEffective;

    public Collection(String id, String userId, String resourceId, Integer idEffective) {
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", isEffective=" + isEffective +
                ", isDeleted=" + isDeleted +
                '}';
    }

    private Integer isDeleted;
}
