package nl.fontys.realestateproject.business.impl.request;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.RequestService;

import nl.fontys.realestateproject.business.dto.request.*;
import nl.fontys.realestateproject.business.exceptions.AlreadyExistingRequestException;
import nl.fontys.realestateproject.domain.enums.RequestStatus;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.RequestRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    RequestRepository requestRepository;
    PropertyRepository propertyRepository;
    RequestConverter requestConverter;
    UserRepository userRepository;

    @Override
    public void createRequest(CreateRequestRequest request) {
        Optional<PropertyEntity> property = propertyRepository.findById(request.getPropertyId());

        if (property.isPresent()) {
            AccountEntity account = userRepository.getReferenceById(request.getAccountId());
            Optional<RequestEntity> alreadyExistingRequest = requestRepository.findPendingPropertyByCustomerIdAndPropertyId(account.getId(), property.get().getId());
            if(alreadyExistingRequest.isPresent()) {
                throw new AlreadyExistingRequestException();
            }
            RequestEntity requestEntity = RequestEntity.builder()
                    .requestStatus(RequestStatus.PENDING.name())
                    .requestDate(LocalDateTime.now())
                    .property(property.get())
                    .account(account)
                    .build();
            requestRepository.save(requestEntity);}
    }

    @Override
    public GetAllRequestResponse getAllByAgentId(long agentId) {
        List<RequestEntity> requests = requestRepository.findAllByAgentId(agentId);
        requests.sort((r1, r2) -> r2.getRequestDate().compareTo(r1.getRequestDate()));
        GetAllRequestResponse response = new GetAllRequestResponse();
        response.setRequests(requests.stream().map(requestConverter::convert).toList());
        return response;
    }

    @Override
    public GetAllRequestResponse getAllByCustomerId(long customerId) {
        return null;
    }

    @Override
    public GetRequestResponse getRequest(long id) {
        return null;
    }

    @Override
    public void updateRequest(UpdateRequestRequest request) {
        Optional<RequestEntity> optionalRequestEntity = requestRepository.findById(request.getRequestId());
        if (optionalRequestEntity.isPresent()) {
            RequestEntity requestEntity = optionalRequestEntity.get();
            requestEntity.setRequestStatus(request.getRequestStatus());
            requestRepository.save(requestEntity);
        }
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
