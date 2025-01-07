package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.dto.request.CreateRequestRequest;
import nl.fontys.realestateproject.business.dto.request.GetAllRequestResponse;
import nl.fontys.realestateproject.business.dto.request.UpdateRequestRequest;
import nl.fontys.realestateproject.business.exceptions.AlreadyExistingRequestException;
import nl.fontys.realestateproject.business.impl.request.RequestConverter;
import nl.fontys.realestateproject.business.impl.request.RequestServiceImpl;
import nl.fontys.realestateproject.domain.Request;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.RequestRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {
    @Mock
    RequestRepository requestRepositoryMock;
    @Mock
    PropertyRepository propertyRepository;
    @Mock
    RequestConverter requestConverter;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    RequestServiceImpl requestService;

    @Test
    void createRequest_ShouldCreateARequest() {
        CreateRequestRequest request = CreateRequestRequest.builder()
                .propertyId(1L)
                .accountId(1L)
                .build();

        when(requestRepositoryMock.save(any(RequestEntity.class))).thenReturn(RequestEntity.builder().id(1L).build());
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(PropertyEntity.builder().id(1L).build()));
        when(userRepository.getReferenceById(1L)).thenReturn(AccountEntity.builder().id(1L).build());
        assertDoesNotThrow(() -> requestService.createRequest(request));
        verify(requestRepositoryMock).save(any(RequestEntity.class));
    }

    @Test
    void createRequest_ShouldThrowAlreadyExistingRequestException_WhenRequestAlreadyExists() {
        CreateRequestRequest request = CreateRequestRequest.builder()
                .propertyId(1L)
                .accountId(1L)
                .build();

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(PropertyEntity.builder().id(1L).build()));
        when(userRepository.getReferenceById(1L)).thenReturn(AccountEntity.builder().id(1L).build());
        when(requestRepositoryMock.findPendingPropertyByCustomerIdAndPropertyId(1L, 1L)).thenReturn(Optional.of(RequestEntity.builder().build()));

        assertThrows(AlreadyExistingRequestException.class, () -> requestService.createRequest(request));
    }

    @Test
    void getAllByAgentId_ShouldReturnAllRequestsByAgentId() {
        List<RequestEntity> requests = new ArrayList<>(List.of(RequestEntity.builder().requestDate(LocalDateTime.now()).build()));
        when(requestRepositoryMock.findAllByAgentId(1L)).thenReturn(requests);
        when(requestConverter.convert(any())).thenReturn(Request.builder().build());
        GetAllRequestResponse response = requestService.getAllByAgentId(1L);
        verify(requestRepositoryMock).findAllByAgentId(1L);
        assertEquals(requests.size(), response.getRequests().size());
    }
    @Test
    void updateRequest_ShouldUpdateRequest() {
        RequestEntity requestEntity = RequestEntity.builder().id(1L).requestStatus("PENDING").build();
        when(requestRepositoryMock.findById(1L)).thenReturn(Optional.of(requestEntity));
        requestService.updateRequest(UpdateRequestRequest.builder().requestId(1L).requestStatus("ACCEPTED").build());
        verify(requestRepositoryMock).save(requestEntity);
    }
    @Test
    void updateRequest_ShouldNotUpdateRequest_WhenRequestDoesNotExist() {
        when(requestRepositoryMock.findById(1L)).thenReturn(Optional.empty());
        requestService.updateRequest(UpdateRequestRequest.builder().requestId(1L).requestStatus("ACCEPTED").build());
        verify(requestRepositoryMock).findById(1L);
    }

}
