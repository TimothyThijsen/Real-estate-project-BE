package nl.fontys.realestateproject.controller;

import nl.fontys.realestateproject.business.dto.user.*;
import nl.fontys.realestateproject.business.impl.account.AccountServiceImpl;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.domain.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;
    @MockBean
    AccountServiceImpl accountService;

    @Test
    void getAllAccount_shouldReturn200ResponseWithAllAccounts() throws Exception {
        // Arrange

        GetAllAccountsResponse response = new GetAllAccountsResponse(List.of(
                Account.builder().id(1).email("mail").firstName("test").lastName("test").roles(List.of(UserRole.CLIENT)).build()));
        when(accountService.getAllAccounts()).thenReturn(response);
        mockMvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "accounts": [
                                    {
                                        "id": 1,
                                        "email": "mail",
                                        "firstName": "test",
                                        "lastName": "test",
                                        "roles": ["CLIENT"]
                                    }
                                ]
                            }
                        """));
        verify(accountService).getAllAccounts();
    }

    @Test
    @WithMockUser(username = "test@mail.com", roles = {"ADMIN"})
    void getAccount_shouldReturn200ResponseWithAccount() throws Exception {
        // Arrange
        Account account = Account.builder().id(1).email("mail").firstName("test").lastName("test").roles(List.of(UserRole.CLIENT)).build();
        GetUserAccountResponse response = new GetUserAccountResponse(account);
        when(accountService.getAccount(1)).thenReturn(response);
        mockMvc.perform(get("/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                            "account": {
                                "id": 1,
                                "email": "mail",
                                "firstName": "test",
                                "lastName": "test",
                                "roles": ["CLIENT"]
                                }
                            }
                        """));
        verify(accountService).getAccount(1);
    }

    @Test
    void login_shouldReturn200ResponseWithLoginResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user@mail.com", "pass");
        LoginResponse response = new LoginResponse("token");
        when(accountService.login(loginRequest)).thenReturn(response);
        mockMvc.perform(post("/accounts/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                                { "email": "user@mail.com", "password": "pass" }
                                """))

                .andDo(print())
                .andExpect(status().isOk());
        verify(accountService).login(loginRequest);
    }

    @Test
    void createAccount_shouldReturn201ResponseWithCreateAccountResponse() throws Exception {
        // Arrange
       /* CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .email("test@mail.com")
                .firstName("test")
                .lastName("test")
                .password("Pass123")
                .role("CLIENT").build();*/
        CreateAccountResponse response = new CreateAccountResponse(1);
        when(accountService.createAccount(any())).thenReturn(response);
        mockMvc.perform(post("/accounts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "email": "test@mail.com",
                                    "firstName": "test",
                                    "lastName": "test",
                                    "password": "Pass123",
                                    "role": "CLIENT"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(accountService).createAccount(any());
    }

    @Test
    @WithMockUser(username = "test@mail.com", roles = {"ADMIN"})
    void updateAccount_shouldReturn204Response() throws Exception {
        // Arrange
        UpdateAccountRequest updateAccountRequest = UpdateAccountRequest.builder()
                .id(1)
                .email("test@mail.com")
                .firstName("test")
                .lastName("test")
                .password("Pass123")
                .role("CLIENT").build();

        AccessTokenImpl accessToken = new AccessTokenImpl("user", 1L, List.of("ADMIN"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);
        doNothing().when(accountService).updateAccount(updateAccountRequest);
        doNothing().when(accountService).updateAccount(updateAccountRequest);
        mockMvc.perform(put("/accounts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "id": 1,
                                    "email": "test@mail.com",
                                    "firstName": "test",
                                    "lastName": "test",
                                    "password": "Pass123",
                                    "role": "CLIENT"
                                    }"""))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(accountService).updateAccount(updateAccountRequest);
    }

    @Test
    @WithMockUser(username = "test@mail.com", roles = {"CLIENT"})
    void updateAccount_shouldReturn403Response() throws Exception {
        AccessTokenImpl accessToken = new AccessTokenImpl("user", 2L, List.of("CLIENT"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);
        doNothing().when(accountService).updateAccount(any());
        mockMvc.perform(put("/accounts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "id": 1,
                                    "email": "test@mail.com",
                                    "firstName": "test",
                                    "lastName": "test",
                                    "password": "Pass123",
                                    "role": "CLIENT"
                                    }"""))
                .andDo(print())
                .andExpect(status().isForbidden());
        verify(accountService, never()).deleteAccount(1);
    }

    @Test
    @WithMockUser(username = "test@mail.com", roles = {"ADMIN"})
    void deleteAccount_shouldReturn204Response() throws Exception {
        // Arrange
        AccessTokenImpl accessToken = new AccessTokenImpl("user", 1L, List.of("ADMIN"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);
        doNothing().when(accountService).deleteAccount(1);
        mockMvc.perform(delete("/accounts/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(accountService).deleteAccount(1);
    }
    @Test
    @WithMockUser(username = "test@mail.com", roles = {"CLIENT"})
    void deleteAccouunt_shouldReturn403Response() throws Exception {
        // Arrange
        AccessTokenImpl accessToken = new AccessTokenImpl("user", 2L, List.of("CLIENT"));
        when(requestAuthenticatedUserProvider.getAuthenticatedUserInRequest()).thenReturn(accessToken);
        doNothing().when(accountService).deleteAccount(1);
        mockMvc.perform(delete("/accounts/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
        verify(accountService, never()).deleteAccount(1);
    }
}
