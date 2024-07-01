package com.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public CouponCountRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // redis 에서 incr 명령어를 사용하기위한 메소드
    public Long increment() {
        return redisTemplate
                .opsForValue()
                .increment("coupon_count");
    }
}
