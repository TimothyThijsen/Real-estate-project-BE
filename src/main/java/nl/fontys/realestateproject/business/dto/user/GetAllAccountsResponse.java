package nl.fontys.realestateproject.business.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Account;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAccountsResponse {
    private List<Account> accountsList;
}
