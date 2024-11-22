package nl.fontys.realestateproject.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.realestateproject.domain.enums.UserRole;

@Entity
@Table(name = "user_role")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AccountEntity user;
}
