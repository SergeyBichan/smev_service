package ru.intervale.smev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.intervale.smev.model.InformationResponse;

import javax.transaction.Transactional;

public interface InfoResponseRepo extends JpaRepository<InformationResponse, Long> {
    @Transactional
    InformationResponse getInformationResponseByVehicleCertificate(String vehicleCertificate);
}
