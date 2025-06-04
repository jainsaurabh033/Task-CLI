package com.taskcli.task_manager.RateLimiter.bucket;

public class TokenBucket {
    private final int capacity;
    private int tokens;
    private long lastRefillTime;
    private final int refillRate;

    public TokenBucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.tokens = capacity;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean consume() {
        refill();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long secondsPassed = (now - lastRefillTime) / 1000;
        int tokensToAdd = (int) (secondsPassed * refillRate);

        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }
}


