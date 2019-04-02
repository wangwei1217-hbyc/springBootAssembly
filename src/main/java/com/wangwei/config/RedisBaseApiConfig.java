package com.wangwei.config;

import com.xiaoma.framework.cache.manager.RedisCacheManager;
import com.xiaoma.framework.cache.manager.impl.RedisCacheManagerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class RedisBaseApiConfig {

    @Value("${redis.jedis.pool.max-idle:6000}")
    private int maxIdle;

    @Value("${redis.jedis.pool.min-idle:6000}")
    private int minIdle;

    @Value("${redis.jedis.pool.max-wait:-1}")
    private long maxWaitMillis;

    @Value("${redis.jedis.pool.max-active:100}")
    private int maxActive;

    @Value("${redis.jedis.pool.timeout:6000}")
    private int timeout;
    @Value("${redis.baseCache.url}")
    private String baseCacheUrl;
    /**
     * jedis 连接池配置
     *
     * @return
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        return jedisPoolConfig;
    }

    /**
     * jedis 连接池集群配置
     *
     * @return
     */
    @Bean(name = "baseCacheJedisPool")
    public ShardedJedisPool baseCacheShardedJedisPool(JedisPoolConfig jedisPoolConfig) {
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>() {
            {
                //redis集群1
                JedisShardInfo jedisShardInfo = new JedisShardInfo(baseCacheUrl);
                //连接超时时间
                jedisShardInfo.setConnectionTimeout(timeout);
                jedisShardInfo.setSoTimeout(timeout);
                add(jedisShardInfo);
            }
        };
        return new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
    }

    /**
     * 缓存工具类
     *
     * @param baseCacheJedisPool
     * @return
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager baseCacheManager(ShardedJedisPool baseCacheJedisPool) {
        RedisCacheManager redisCacheManager = new RedisCacheManagerImpl();
        redisCacheManager.setJedisPool(baseCacheJedisPool);
        return redisCacheManager;
    }

}
