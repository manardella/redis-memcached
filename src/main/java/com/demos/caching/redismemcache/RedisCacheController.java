package com.demos.caching.redismemcache;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisCacheController {

    private final RedisLocalCache redisLocalCache;

    public RedisCacheController(final RedisLocalCache redisLocalCache) {
        this.redisLocalCache = redisLocalCache;
    }


    @GetMapping("/redis/key/{key}")
    public ResponseEntity<String> getKey(@Validated @PathVariable("key") final String key) {
        return redisLocalCache.getString(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/redis/key/{key}")
    public ResponseEntity<String> deleteKey(@Validated @PathVariable("key") final String key) {
        redisLocalCache.delete(key);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redis/key")
    public ResponseEntity getKey(@RequestBody final WriteKeyRequest writeKeyRequest) {
        redisLocalCache.write(writeKeyRequest);
        return ResponseEntity.ok().build();
    }
}
