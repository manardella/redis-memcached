package com.demos.caching.redismemcache.engine;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Data
@Builder
@Setter
@EqualsAndHashCode
@ToString
public class TaskExecutionResult {
    private final Optional<Object> value;
    private final Optional<Exception> exception;
}
