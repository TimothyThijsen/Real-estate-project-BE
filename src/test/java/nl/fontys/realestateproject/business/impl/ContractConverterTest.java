package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.business.impl.contract.ContractConverter;
import nl.fontys.realestateproject.domain.Contract;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.ContractEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ContractConverterTest {
    @Mock
    TransactionConverter transactionConverter;
    @Mock
    PropertyConverter propertyConverter;
    @Mock
    AccountConverter accountConverter;

    @InjectMocks
    ContractConverter contractConverter;

    @Test
    void convert_ShouldConvertContractEntityToContract() {

        AccountEntity customerEntity = AccountEntity.builder()
                .id(1L)
                .email("mail")
                .build();

        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(1L)
                .build();

        ContractEntity contractEntity = ContractEntity.builder()
                .id(1L)
                .customer(customerEntity)
                .property(propertyEntity)
                .isActive(true)
                .minimumContractEndDate(LocalDate.now().atStartOfDay())
                .startDate(LocalDate.now().atStartOfDay())
                .transactions(List.of(TransactionEntity.builder().id(1L).build()))
                .build();

        Contract contract = contractConverter.convert(contractEntity);

        assertEquals(1L, contract.getId());
    }

    @Test
    void convert_ShouldThrowException_WhenContractEntityIsNull() {
        ContractEntity contractEntity = null;

        assertThrows(NullPointerException.class, () -> contractConverter.convert(contractEntity));
    }
}
