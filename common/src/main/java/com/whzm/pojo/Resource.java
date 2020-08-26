package com.whzm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.pojo
 * @Author: hq
 * @CreateTime: 2020-08-13 20:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "i_rate_of_flow")
public class Resource implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Integer)
    private Integer type;
    @Field(type = FieldType.Text)
    private String userId;
    @Field(type = FieldType.Text)
    private String number;
    @Field(type = FieldType.Text)
    private String company;
    @Field(type = FieldType.Text)
    private String createTime;
    @Field(type = FieldType.Text)
    private Integer status;
    @Field(type = FieldType.Text)
    private String updateUser;
    @Field(type = FieldType.Text)
    private String updateTime;
    @Field(type = FieldType.Integer)
    private Integer isDeleted;
    @Field(type = FieldType.Integer)
    private Integer top;
    @Field(type = FieldType.Text)
    private String aliveTime;
    @Field(type = FieldType.Text)
    private String detail;

}
