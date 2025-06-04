package com.taskcli.task_manager.RateLimiter;

import com.taskcli.task_manager.RateLimiter.config.RateLimiterConfig;
import com.taskcli.task_manager.RateLimiter.limiter.RateLimiter;
import com.taskcli.task_manager.RateLimiter.limiter.TokenBucketRateLimiter;
import org.springframework.stereotype.Component;

@Component
public class RequestLimiter {

    private final RateLimiter rateLimiter;

    public RequestLimiter(){
        this.rateLimiter = new TokenBucketRateLimiter(new RateLimiterConfig());
    }

    public boolean isAllowed(String userId){
        return rateLimiter.allowRequest(userId);
    }
}
