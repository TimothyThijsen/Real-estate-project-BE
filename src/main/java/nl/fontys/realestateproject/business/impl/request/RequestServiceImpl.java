package nl.fontys.realestateproject.business.impl.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.business.RequestService;

import nl.fontys.realestateproject.business.dto.request.*;
import nl.fontys.realestateproject.domain.enums.RequestStatus;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.RequestRepository;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    RequestRepository requestRepository;
    PropertyRepository propertyRepository;
    RequestConverter requestConverter;

    @Override
    public void createRequest(CreateRequestRequest request) {

        PropertyEntity property = propertyRepository.getById(request.getPropertyId());

        RequestEntity requestEntity = RequestEntity.builder()
                .requestStatus(RequestStatus.PENDING.name())
                .requestDate(LocalDateTime.now())
                .property(property)
                .accountId(request.getAccountId())
                .build();
        requestRepository.save(requestEntity);

    }

    @Override
    public GetAllRequestResponse getAllRequests(long agentId) {
        GetAllRequestResponse response = new GetAllRequestResponse();
        response.setRequests(requestRepository.findAll().stream().map(requestConverter::convert).toList());
        return response;
    }

    @Override
    public GetRequestResponse getRequest(long id) {
        return null;
    }

    @Override
    public void updateRequest(UpdateRequestRequest request) {
        RequestEntity requestEntity = requestRepository.getReferenceById(request.getRequestId());
        requestEntity.setRequestStatus(request.getRequestStatus());
        requestRepository.save(requestEntity);
    }

    @Override
    public void deleteRequest(long id) {
       throw new NotImplementedException();
    }

    @Override
    public GetActiveRequestsResponse getActiveRequests(long customerId, long propertyId) {
        RequestEntity requestEntity = requestRepository.findActivePropertyByCustomerIdAndPropertyId(customerId, propertyId);
        return GetActiveRequestsResponse.builder()
                .hasActiveRequests(requestEntity != null)
                .build();
    }
}
