package com.demos.caching.redismemcache.engine;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class ExecutorEngine {
    private final ExecutorService executorService = Executors.newWorkStealingPool();

    public void submitTask(final TaskToRun task, final Consumer<TaskExecutionResult> onSuccess, final Consumer<TaskExecutionResult> onError) {
        executorService.execute(() -> {
            try {
                final Object executionResult = task.getTask().execute();
                onSuccess.accept(TaskExecutionResult.builder().value(Optional.ofNullable(executionResult)).build());
            } catch (Exception e) {
                onError.accept(TaskExecutionResult.builder().exception(Optional.ofNullable(e)).build());
            }
        });
    }

}
