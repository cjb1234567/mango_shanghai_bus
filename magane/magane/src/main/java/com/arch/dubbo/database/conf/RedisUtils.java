package com.arch.dubbo.database.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> redisScript;

    private RedisSerializer<String> argsSerializer;

    private RedisSerializer resultSerializer;

    private final Long EXEC_RESULT = 1L;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        argsSerializer = new StringRedisSerializer();
        resultSerializer = new StringRedisSerializer();

        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
    }

    public boolean getLock(String key, String requestId, String expireTime) {
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/lock.lua")));
        Object result = redisTemplate.execute(redisScript, argsSerializer, resultSerializer, Collections.singletonList(key), requestId, expireTime);
        if (EXEC_RESULT.equals(result)) {
            return true;
        }
        return false;
    }

    public boolean releaseLock(String key, String requestId) {
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/releaseLock.lua")));
        Object result = redisTemplate.execute(redisScript, argsSerializer, resultSerializer, Collections.singletonList(key), requestId);
        if (EXEC_RESULT.equals(result)) {
            return true;
        }
        return false;
    }

}
