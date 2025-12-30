package br.com.carlos.deploy_app_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cupom")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CupomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;
    private Double discount;
    private Boolean valid;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private br.com.carlos.deploy_app_spring.entity.EventEntity event;


    public void setValid(Date date) {
    }
}
