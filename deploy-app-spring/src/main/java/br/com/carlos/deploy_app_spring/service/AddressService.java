package br.com.carlos.deploy_app_spring.service;

import br.com.carlos.deploy_app_spring.dto.request.EventRequestDto;
import br.com.carlos.deploy_app_spring.entity.AddressEntity;
import br.com.carlos.deploy_app_spring.entity.EventEntity;
import br.com.carlos.deploy_app_spring.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public void createAddress(EventRequestDto data, EventEntity event) {
        AddressEntity address = new AddressEntity();
        address.setCity(data.city());
        address.setUf(data.state());
        address.setEvent(event);
        addressRepository.save(address);
    }
}