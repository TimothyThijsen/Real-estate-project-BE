package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.dto.request.UpdateRequestRequest;
import nl.fontys.realestateproject.business.dto.request.CreateRequestRequest;
import nl.fontys.realestateproject.business.dto.request.GetAllRequestResponse;
import nl.fontys.realestateproject.business.dto.request.GetRequestResponse;

public interface RequestService {
    void createRequest(CreateRequestRequest request);

    GetAllRequestResponse getAllRequests();

    GetRequestResponse getRequest(long id);

    void updateRequest(UpdateRequestRequest request);

    void deleteRequest(long id);
}
