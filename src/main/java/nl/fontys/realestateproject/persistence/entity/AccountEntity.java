package nl.fontys.realestateproject.persistence.entity;

import lombok.Builder;
import lombok.Data;
import nl.fontys.realestateproject.domain.Enums.UserRole;

@Data
@Builder
public class AccountEntity {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private UserRole role;
}
