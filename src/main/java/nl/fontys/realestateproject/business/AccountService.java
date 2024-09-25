package nl.fontys.realestateproject.business;


import nl.fontys.realestateproject.domain.User.CreateAccountRequest;
import nl.fontys.realestateproject.domain.User.CreateAccountResponse;
import nl.fontys.realestateproject.domain.User.GetAllAccountsResponse;

public interface AccountService {
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
    public GetAllAccountsResponse getAllAccounts();
}
