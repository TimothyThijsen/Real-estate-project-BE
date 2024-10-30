package nl.fontys.realestateproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.enums.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    private UserRole role;
}
