package nl.fontys.realestateproject.business.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserAccountResponse {
    private Account account;
}
