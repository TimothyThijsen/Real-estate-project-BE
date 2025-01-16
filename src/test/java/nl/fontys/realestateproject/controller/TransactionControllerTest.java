package nl.fontys.realestateproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.impl.TransactionServiceImpl;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.configuration.security.token.impl.AccessTokenImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;
    @MockBean
    TransactionServiceImpl transactionService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
    void makeTransaction_shouldReturnCreated_whenRequestIsValid() throws Exception {
        MakeTransactionRequest request = new MakeTransactionRequest();
        request.setCustomerId(1L);
        request.setPropertyId(1L);
        AccessTokenImpl accessToken = new AccessTokenImpl("user", 1L, List.of("AGENT"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
    void getAllTransactions_shouldReturnOk_whenUserIsAdmin() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "client@mail.com", roles = {"CLIENT"})
    void getAllTransactionsByCustomerId_shouldReturnOk_whenUserIsClient() throws Exception {
        int customerId = 1;

        mockMvc.perform(get("/transactions/customer/{customerId}", customerId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "agent@mail.com", roles = {"AGENT"})
    void getAllTransactionsByPropertyId_shouldReturnOk_whenUserIsAgent() throws Exception {
        int propertyId = 1;

        mockMvc.perform(get("/transactions/property/{propertyId}", propertyId))
                .andExpect(status().isOk());
    }

}
