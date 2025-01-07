package nl.fontys.realestateproject.business.impl.request;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.business.impl.PropertyConverter;
import nl.fontys.realestateproject.domain.Request;
import nl.fontys.realestateproject.domain.enums.RequestStatus;
import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public final class RequestConverter {
    AccountConverter accountConverter;
    PropertyConverter propertyConverter;
    public Request convert(RequestEntity entity) {
        return Request.builder()
                .id(entity.getId())
                .account(accountConverter.convert(entity.getAccount()))
                .property(propertyConverter.convert(entity.getProperty()))
                .status(RequestStatus.valueOf(entity.getRequestStatus()))
                .requestDate(entity.getRequestDate())
                .build();
    }
}
