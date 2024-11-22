package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.domain.enums.UserRole;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountConverterTest {

    @Test
    void convert_ShouldReturnAccount() {
        // Arrange
        AccountConverter accountConverter = new AccountConverter();
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .email("fake@fake.com")
                .firstName("name")
                .lastName("last")
                .userRoles(Set.of(UserRoleEntity.builder().role(UserRole.CLIENT).build()))
                .password("12345").build();
        Account response = accountConverter.convert(accountEntity);
        assertEquals(1L, response.getId());
        assertDoesNotThrow(() -> accountConverter.convert(accountEntity));
    }

    @Test
    void convert_ShouldThrowException_WhenAccountEntityIsNull() {
        AccountConverter accountConverter = new AccountConverter();
        AccountEntity accountEntity = null;
        assertThrows(NullPointerException.class, () -> accountConverter.convert(accountEntity));
    }
}
