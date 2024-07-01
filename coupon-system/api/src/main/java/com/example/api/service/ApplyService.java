package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.AppliedUserRepository;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository,
                        CouponCountRepository couponCountRepository,
                        CouponCreateProducer couponCreateProducer,
                        AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {
        Long apply = appliedUserRepository.add(userId);

        if (apply != 1) {
            // apply 가 1이 아니라면, 이 유저는 쿠폰을 이미 발급받은 것임, 쿠폰을 발급하지 않고 return
            return;
        }

//        long count = couponRepository.count(); // mysql 에서 쿠폰 카운트를 가지고 오는 로직
        Long count = couponCountRepository.increment(); // redis 에서 쿠폰 카운트를 가지고 오는 로직

        if (count > 100) {
            return;
        }

//        couponRepository.save(new Coupon(userId));  // 직접 쿠폰을 생성하는 로직
        couponCreateProducer.create(userId);    // CouponCreateProducer 를 사용해서 Topic 에 userId 를 전송하는 로직
    }

}
