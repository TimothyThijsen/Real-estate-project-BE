package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;


final class AccountConverter {
    public static Account convert(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .email(accountEntity.getEmail())
                .firstName(accountEntity.getFirstName())
                .lastName(accountEntity.getLastName())
                .role(accountEntity.getRole())
                .build();
    }
}
