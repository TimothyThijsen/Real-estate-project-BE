package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.impl.contract.ContractConverter;
import nl.fontys.realestateproject.domain.Contract;
import nl.fontys.realestateproject.persistence.entity.ContractEntity;
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
    @InjectMocks
    ContractConverter contractConverter;

    @Test
    void convert_ShouldConvertContractEntityToContract() {

        ContractEntity contractEntity = ContractEntity.builder()
                .id(1L)
                .customerId(1L)
                .propertyId(1L)
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
