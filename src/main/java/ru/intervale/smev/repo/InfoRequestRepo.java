package ru.intervale.smev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.Penalty;

@Repository
public interface InfoRequestRepo extends JpaRepository<InformationRequest, Long> {
   InformationRequest findByVehicleCertificate(String vehicleCertificate);
}
