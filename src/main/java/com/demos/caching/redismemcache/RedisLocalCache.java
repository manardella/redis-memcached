package com.demos.caching.redismemcache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;

@Component
public class RedisLocalCache implements RedisCache {

    private final JedisPool pool;

    public RedisLocalCache(@Value("${spring.redis.host}") final String redisHost) {
        this.pool = new JedisPool(new JedisPoolConfig(), redisHost);
    }

    @Override
    public Optional<String> getString(final String key) {
        try (Jedis jedis = pool.getResource()) {
            return Optional.ofNullable(jedis.get(key));
        }
    }

    @Override
    public void write(final WriteKeyRequest writeKeyRequest) {
        if (writeKeyRequest.hasToExpire()) {
            setexString(writeKeyRequest.getKey(), writeKeyRequest.getValue(), writeKeyRequest.getExpiresInSeconds());
        } else {
            setString(writeKeyRequest.getKey(), writeKeyRequest.getValue());
        }
    }

    @Override
    public void delete(final String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

    private void setString(final String key, final String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
        }
    }

    private void setexString(final String key, final String value, final int expireInSeconds) {
        try (Jedis jedis = pool.getResource()) {
            jedis.setex(key, expireInSeconds, value);
        }
    }


}


