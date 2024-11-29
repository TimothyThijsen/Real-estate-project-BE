package nl.fontys.realestateproject.business.impl.Request;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.domain.Request;
import nl.fontys.realestateproject.domain.enums.RequestStatus;
import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public final class RequestConverter {

    public Request convert(RequestEntity entity) {
        return Request.builder()
                .id(entity.getId())
                .accountId(entity.getCustomerId())
                .propertyId(entity.getProperty().getId())
                .status(RequestStatus.valueOf(entity.getRequestStatus()))
                .requestDate(entity.getRequestDate())
                .build();
    }
}
