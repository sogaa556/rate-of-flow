package com.whzm.service;

import com.whzm.pojo.Collection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: rate-of-flow
 * @BelongsPackage: com.whzm.service
 * @Author: hq
 * @CreateTime: 2020-08-24 16:29
 * @Description:
 */
// 如果你想让 OrderEsService 具备操作es的功能
// 需要继承ElasticsearchRepository<实体类的类型，实体类主键的类型>
// 继承以后 OrderEsService 就具备了crud 包括复杂查询
// 不需要创建实现类
@Service
public interface CollectionEsService extends ElasticsearchRepository<Collection,String>{

}
