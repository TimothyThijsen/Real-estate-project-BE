package nl.fontys.realestateproject.business.impl;

import jakarta.persistence.Converter;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import org.springframework.stereotype.Service;

@Service
final class AccountConverter {
    public  Account convert(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .email(accountEntity.getEmail())
                .firstName(accountEntity.getFirstName())
                .lastName(accountEntity.getLastName())
                .role(accountEntity.getRole())
                .build();
    }
}
