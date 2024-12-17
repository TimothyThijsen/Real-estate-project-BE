package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.dto.request.*;

public interface RequestService {
    void createRequest(CreateRequestRequest request);

    GetAllRequestResponse getAllRequests(long agentId);

    GetRequestResponse getRequest(long id);

    void updateRequest(UpdateRequestRequest request);

    void deleteRequest(long id);
    GetActiveRequestsResponse getActiveRequests(long customerId, long propertyId);
}
