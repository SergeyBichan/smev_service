package ru.intervale.smev.utils;

import org.springframework.transaction.annotation.Transactional;
import ru.intervale.smev.model.InformationRequest;
import ru.intervale.smev.model.InformationResponse;



public interface WorkerBehavior {
    @Transactional
    void save(InformationResponse response);
    void delete(Object obj);
}
