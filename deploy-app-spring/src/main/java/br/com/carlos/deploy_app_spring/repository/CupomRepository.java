package br.com.carlos.deploy_app_spring.repository;

import br.com.carlos.deploy_app_spring.entity.CupomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CupomRepository extends JpaRepository<CupomEntity, UUID> {
}
