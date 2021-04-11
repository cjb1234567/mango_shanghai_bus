package com.arch.dubbo.manage;

import com.arch.dubbo.database.conf.RedisUtils;
import com.arch.service.CommonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
class ManageApplicationTests {
//
//    @Autowired
//    private RedisUtils redisUtils;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private CommonService commonService;


    @Test
    void contextLoads() {
//        boolean flag = redisUtils.getLock("tag", "ysytag", "1000");
//        String ans = (String) redisTemplate.opsForValue().get("tag");
//
//        boolean flag1 = redisUtils.releaseLock("tag", "ysytag");
//        Assert.isTrue("world".equals(ans));
    }

}
