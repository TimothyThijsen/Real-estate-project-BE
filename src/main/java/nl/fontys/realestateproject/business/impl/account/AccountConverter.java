package nl.fontys.realestateproject.business.impl.account;

import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.UserRoleEntity;
import org.springframework.stereotype.Service;

@Service
public final class AccountConverter {
    public Account convert(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .email(accountEntity.getEmail())
                .firstName(accountEntity.getFirstName())
                .lastName(accountEntity.getLastName())
                .roles(accountEntity.getUserRoles().stream().map(UserRoleEntity::getRole).toList())
                .build();
    }
}
