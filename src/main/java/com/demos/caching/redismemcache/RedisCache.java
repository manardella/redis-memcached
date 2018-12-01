package com.demos.caching.redismemcache;

import java.util.Optional;

public interface RedisCache {
    Optional<String> getString(String key);

    void write(WriteKeyRequest writeKeyRequest);

    void delete(String key);
}
