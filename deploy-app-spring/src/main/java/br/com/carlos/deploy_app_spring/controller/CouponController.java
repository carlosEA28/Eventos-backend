package br.com.carlos.deploy_app_spring.controller;

import br.com.carlos.deploy_app_spring.dto.request.CouponRequestDTO;
import br.com.carlos.deploy_app_spring.entity.CupomEntity;
import br.com.carlos.deploy_app_spring.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<CupomEntity> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {
        CupomEntity coupons = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupons);
    }
}