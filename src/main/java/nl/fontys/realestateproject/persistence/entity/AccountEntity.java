package nl.fontys.realestateproject.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import nl.fontys.realestateproject.domain.User.Enums.UserRole;

@Data
@Builder
public class AccountEntity {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    private UserRole role;
}
