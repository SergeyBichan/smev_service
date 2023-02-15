package ru.intervale.smev.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class InformationResponse {
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
