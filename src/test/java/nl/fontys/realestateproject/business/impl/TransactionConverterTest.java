package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Transaction;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionConverterTest {
    @InjectMocks
    TransactionConverter transactionConverter;

    @Test
    void convert_ShouldReturnTransaction() {
        TransactionEntity entity = TransactionEntity.builder()
                .id(1L)
                .customerId(1L)
                .propertyId(1L)
                .build();
        Transaction response = transactionConverter.convert(entity);
        assertEquals(1L, response.getId());
        assertDoesNotThrow(() -> transactionConverter.convert(entity));
    }
    @Test
    void convert_ShouldThrowException_WhenAccountEntityIsNull() {
        TransactionEntity entity = null;
        assertThrows(NullPointerException.class, () -> transactionConverter.convert(entity));
    }
}
