package ru.intervale.smev.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@ToString
@Entity
public class InformationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "information_request")
    private String vehicleCertificate;
}
