package com.whzm;

import com.whzm.pojo.Resource;
import com.whzm.service.ResourceEsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    ResourceEsService resourceEsService;

    @Test
    void contextLoads() {
        Page<Resource> all = resourceEsService.findAll(PageRequest.of(0, 10));
        System.out.println(all.getContent());
    }

}
