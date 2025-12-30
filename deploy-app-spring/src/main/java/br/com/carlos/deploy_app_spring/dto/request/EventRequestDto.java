package br.com.carlos.deploy_app_spring.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record EventRequestDto(String title, String description, String city, String state,String eventUrl, boolean remote, MultipartFile image,Long date) {
}
