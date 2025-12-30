package br.com.carlos.deploy_app_spring.service;

import br.com.carlos.deploy_app_spring.dto.request.EventRequestDto;
import br.com.carlos.deploy_app_spring.dto.response.EventResponseDTO;
import br.com.carlos.deploy_app_spring.entity.EventEntity;
import br.com.carlos.deploy_app_spring.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private AddressService addressService;

    @Autowired
    private EventRepository eventRepository;

    public EventEntity createEvent(EventRequestDto dto){
        String imgUrl = null;

        if(dto.image() != null){
            imgUrl = this.uploadImage(dto.image());
        }

        EventEntity event = new EventEntity();
        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setImgUrl(imgUrl);
        event.setEventUrl(dto.eventUrl());
        event.setRemote(dto.remote());
        event.setDate(new Date(dto.date()));

        event = eventRepository.save(event);

        if(!dto.remote()) {
            this.addressService.createAddress(dto, event);
        }

        return event;


    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<EventEntity> eventsPage = eventRepository.findUpcomingEvents(new Date(), pageable);

        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                "",
                "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl()
        )).toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String uf, Date startDate, Date endDate){
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Pageable pageable = PageRequest.of(page, size);

        Page<EventEntity> eventsPage = this.eventRepository.findFilteredEvents(city, uf, startDate, endDate, pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        "",
                        "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .stream().toList();
    }


    private String uploadImage(MultipartFile image) {
        String imageName = UUID.randomUUID() + "-" + image.getOriginalFilename();

        try {
            File file = this.convertMultipartFile(image);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
            file.delete();

            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(imageName)
                    .build();

            return s3Client.utilities().getUrl(getUrlRequest).toExternalForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultipartFile(MultipartFile image) throws IOException {
        File convFile = new File(Objects.requireNonNull(image.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(image.getBytes());
        fos.close();
        return convFile;
    }
}
