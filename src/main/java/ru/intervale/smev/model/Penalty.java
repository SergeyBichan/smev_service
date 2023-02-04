package ru.intervale.smev.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "penalties")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "accruedAmount")
    private int accruedAmount;
    @Column(name = "paymentAmount")
    private int paymentAmount;
    @Column(name = "decreeNumber")
    private Long decreeNumber;
    @Column(name = "vehicleCertificate")
    private String vehicleCertificate;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "articleKOAP")
    private String articleKOAP;
}