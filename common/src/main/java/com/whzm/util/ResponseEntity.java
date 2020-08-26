package com.whzm.util;

import java.util.List;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.util
 * @Author: 吴严
 * @CreateTime: 2020-08-12 09:56
 * @Description: 后端向前端响应json数据工具类
 */
public class ResponseEntity<T> {
    private Integer code;
    private String msg;
    private T data;
    private Integer count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseEntity(Integer code, String msg, T data, Integer count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public ResponseEntity(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseEntity(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseEntity(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity() {
    }

    /**
     * 快捷生成响应到前端的数据格式，适用于返回值为List格式，用于分页查询
     * @param code
     * @param msg
     * @param data
     * @param count
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> pageSuccess(Integer code, String msg, T data, Integer count) {
        return new ResponseEntity(code, msg, data, count);
    }

    /**
     * 快捷生成响应到前端的数据格式，适用于返回值为实体类格式，用于查询得到一个对象
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> entitySuccess(Integer code, String msg, T data) {
        return new ResponseEntity(code, msg, data);
    }

    /**
     * 快捷生成响应到前端的数据格式，适用于做增删改操作，返回值为整数（只需要往前端传递状态码和提示消息）
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> tipsSuccess(Integer code, String msg) {
        return new ResponseEntity(code, msg);
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", data=" + data +
            ", count=" + count +
            '}';
    }
}
