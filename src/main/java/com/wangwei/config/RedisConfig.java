package com.wangwei.config;

import com.xiaoma.util.RedisTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RedisConfig
 * @Description Redis 配置类
 */
//@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private String database;

    @Bean
    public JedisShardInfo getJedisShardInfo() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
        if(password != null && !"".equals(password)){
            jedisShardInfo.setPassword(password);
        }
        return jedisShardInfo;
    }

    /**
     * 自定义redisTemplate
     * 封装常用redis操作
     * @return
     */
    @Bean(value = "redisTemplate")
    public RedisTemplate redisTemplate() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);

        RedisTemplate template = new RedisTemplate();
        int db = 0;
        if (StringUtils.isNotEmpty(database)) {
            db = Integer.valueOf(database);
        }
        int p = 6379;
        if (StringUtils.isNotEmpty(port)) {
            p = Integer.valueOf(port);
        }
        JedisShardInfo shardInfo = template.getJedisShardInfo(host, p, password, db);
        List<JedisShardInfo> shards = new ArrayList<>();
        shards.add(shardInfo);
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
        template.setShardedJedisPool(shardedJedisPool);
        return template;
    }

}

