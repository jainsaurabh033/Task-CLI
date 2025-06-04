package com.taskcli.task_manager.RateLimiter.limiter;

public interface RateLimiter {
    boolean allowRequest(String userId);
}
