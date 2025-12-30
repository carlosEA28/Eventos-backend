package br.com.carlos.deploy_app_spring.service;

import br.com.carlos.deploy_app_spring.dto.request.CouponRequestDTO;
import br.com.carlos.deploy_app_spring.entity.CupomEntity;
import br.com.carlos.deploy_app_spring.entity.EventEntity;
import br.com.carlos.deploy_app_spring.repository.CupomRepository;
import br.com.carlos.deploy_app_spring.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CupomRepository couponRepository;
    private final EventRepository eventRepository;

    public CupomEntity addCouponToEvent(UUID eventId, CouponRequestDTO couponData) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        var coupon = new CupomEntity();
        coupon.setCode(couponData.code());
        coupon.setDiscount(Double.valueOf(couponData.discount()));
        coupon.setValid(new Date(couponData.valid()));
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }
}