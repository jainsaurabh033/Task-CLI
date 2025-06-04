package com.taskcli.task_manager.RateLimiter.limiter;

import com.taskcli.task_manager.RateLimiter.bucket.TokenBucket;
import com.taskcli.task_manager.RateLimiter.config.RateLimiterConfig;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketRateLimiter implements RateLimiter {
    private final Map<String, TokenBucket> userBuckets = new HashMap<>();
    private final RateLimiterConfig config;

    public TokenBucketRateLimiter(RateLimiterConfig config){
        this.config = config;
    }

    public boolean allowRequest(String userId){
        userBuckets.putIfAbsent(userId, createBucket(userId));
        return userBuckets.get(userId).consume();
    }

    private TokenBucket createBucket(String userId){
        int capacity = config.getCapacityForUser(userId);
        int rate = config.getRefillRateForUser(userId);
        return new TokenBucket(capacity,rate);
    }
}
