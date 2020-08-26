package com.whzm.service;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: springcloud-demo2
 * @BelongsPackage: com.example.user.service
 * @Author: hq
 * @CreateTime: 2020-07-29 14:41
 * @Description:
 */
public interface RedisService {

    // 向redis中存入一个键值对
    public boolean set(final String key, Object value);

    // 向redis中存入一个键值对 并设置过期时间
    public boolean set(final String key, Object value, Long expireTime);

    // 删除redis缓存 一次可以删除多个redis缓存
    public void remove(final String... keys);

    // 模糊匹配 key  并删除
    public void removePattern(final String pattern);

    public void remove(final String key);

    public boolean exists(final String key);

    public Object get(final String key);

    public void hmSet(String key, Object hashKey, Object value);

    public Object hmGet(String key, Object hashKey);

    public void lPush(String k, Object v);

    public List<Object> lRange(String k, long l, long l1);

    public void add(String key, Object value);

    public Set<Object> setMembers(String key);

    public void zAdd(String key, Object value, double scoure);

    public Set<Object> rangeByScore(String key, double scoure, double scoure1);


}
