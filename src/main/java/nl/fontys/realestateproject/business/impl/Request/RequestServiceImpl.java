package nl.fontys.realestateproject.business.impl.Request;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.RequestService;
import nl.fontys.realestateproject.business.dto.request.CreateRequestRequest;
import nl.fontys.realestateproject.business.dto.request.GetAllRequestResponse;
import nl.fontys.realestateproject.business.dto.request.GetRequestResponse;
import nl.fontys.realestateproject.business.dto.request.UpdateRequestRequest;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.RequestRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    RequestRepository requestRepository;
    PropertyRepository propertyRepository;
    UserRepository userRepository;
    RequestConverter requestConverter;
    @Override
    public void createRequest(CreateRequestRequest request) {

        PropertyEntity property = propertyRepository.getReferenceById(request.getPropertyId());

        RequestEntity requestEntity = RequestEntity.builder()
                .requestStatus(request.getRequestStatus())
                .requestDate(LocalDateTime.now())
                .property(property)
                .customerId(request.getAccountId())
                .build();
        requestRepository.save(requestEntity);

    }

    @Override
    public GetAllRequestResponse getAllRequests() {
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

    }
}
