package nl.fontys.realestateproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fontys.realestateproject.business.dto.request.CreateRequestRequest;
import nl.fontys.realestateproject.business.dto.request.GetActiveRequestsResponse;
import nl.fontys.realestateproject.business.dto.request.GetAllRequestResponse;
import nl.fontys.realestateproject.business.dto.request.UpdateRequestRequest;
import nl.fontys.realestateproject.business.impl.request.RequestServiceImpl;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.configuration.security.token.impl.AccessTokenImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;
    @MockBean
    RequestServiceImpl requestService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "client@mail.com", roles = {"AGENT"})
    void getAllByAgentId_shouldReturnExistingRequest() throws Exception {

        when(requestService.getAllByAgentId(1L)).thenReturn(new GetAllRequestResponse());
        mockMvc.perform(get("/requests/agent")
                        .param("agentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @WithMockUser(username = "client@mail.com", roles = {"CUSTOMER"})
    void getAllByCustomerId_shouldReturnExistingRequest() throws Exception {
        when(requestService.getAllByCustomerId(1L)).thenReturn(new GetAllRequestResponse());
        mockMvc.perform(get("/requests/customer")
                        .param("customerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "client@mail.com", roles = {"CUSTOMER"})
    void createRequest_shouldReturn201WhenValid() throws Exception {
        CreateRequestRequest request = new CreateRequestRequest();
        request.setPropertyId(1L);
        request.setAccountId(1L);
        AccessTokenImpl accessToken = new AccessTokenImpl("user", 1L, List.of("CUSTOMER"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);
        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "client@mail.com", roles = {"CUSTOMER"})
    void updateRequest_shouldReturn201WhenValid() throws Exception {
        UpdateRequestRequest request = new UpdateRequestRequest();
        request.setRequestId(1L);
        request.setRequestStatus("ACCEPTED");
        AccessTokenImpl accessToken = new AccessTokenImpl("user", 1L, List.of("CUSTOMER"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);

        mockMvc.perform(put("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "client@mail.com", roles = {"CUSTOMER"})
    void getActiveRequest_ShouldGetActiveRequests() throws Exception {
        when(requestService.getActiveRequests(1L, 1L)).thenReturn(new GetActiveRequestsResponse());
        mockMvc.perform(get("/requests/active")
                        .param("customerId", "1")
                        .param("propertyId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}