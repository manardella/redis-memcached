package com.demos.caching.redismemcache;

import com.demos.caching.redismemcache.engine.ExecutorEngine;
import com.demos.caching.redismemcache.engine.TaskToRun;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.out;

@RestController
public class RedisCacheController {

    private final ExecutorEngine executorEngine;
    private final RedisLocalCache redisLocalCache;

    public RedisCacheController(final ExecutorEngine executorEngine, final RedisLocalCache redisLocalCache) {
        this.executorEngine = executorEngine;
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

    @PostMapping("/redis/key/no-ack")
    public ResponseEntity getKey(@RequestBody final WriteKeyRequest writeKeyRequest) {

        final TaskToRun taskToRun = TaskToRun.builder().task(() -> {
            redisLocalCache.write(writeKeyRequest);
            return 0;
        }).build();

        executorEngine.submitTask(
                taskToRun,
                (taskExecutionResult) -> out.println("[Correct][/redis/key/no-ack] result=" + taskExecutionResult.getValue()),
                (taskExecutionResult) -> out.println("[Error][/redis/key/no-ack] exception=" + taskExecutionResult.getException()));

        return ResponseEntity.ok().build();
    }
}
