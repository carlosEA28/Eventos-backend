package br.com.carlos.deploy_app_spring.dto.request;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}