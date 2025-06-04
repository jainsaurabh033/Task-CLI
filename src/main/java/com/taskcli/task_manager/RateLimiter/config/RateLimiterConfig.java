package com.taskcli.task_manager.RateLimiter.config;

public class RateLimiterConfig {
    private final int capacity = 5;
    private final int refillRate = 1;

    public int getCapacityForUser(String userId){
        return capacity;
    }

    public int getRefillRateForUser(String userId){
        return refillRate;
    }
}
