package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.dto.transaction.GetAllTransactionResponse;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionResponse;
import nl.fontys.realestateproject.business.exceptions.TransactionException;
import nl.fontys.realestateproject.domain.Transaction;
import nl.fontys.realestateproject.persistence.TransactionRepository;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepositoryMock;
    @Mock
    TransactionConverter transactionConverter;
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void makeTransaction_ShouldMakeATransaction() {
        MakeTransactionRequest request = MakeTransactionRequest.builder()
                .customerId(1L)
                .propertyId(1L)
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder().id(1L).build();
        when(transactionRepositoryMock.save(any(TransactionEntity.class))).thenReturn(transactionEntity);
        MakeTransactionResponse actual = transactionService.makeTransaction(request);

        verify(transactionRepositoryMock).save(any(TransactionEntity.class));
        assertEquals(1, actual.getTransactionId());
    }
    @Test
    void makeTransaction_ShouldThrowTransactionException() {
        MakeTransactionRequest request = MakeTransactionRequest.builder()
                .customerId(1L)
                .propertyId(1L)
                .build();

        when(transactionRepositoryMock.save(any(TransactionEntity.class))).thenThrow(new RuntimeException());
        assertThrows(TransactionException.class, () -> transactionService.makeTransaction(request));
    }

    @Test
    void getAllTransactions_ShouldReturnAllTransactions() {
        TransactionEntity transactionEntity = TransactionEntity.builder().id(1L).customerId(1L).propertyId(1L).build();

        when(transactionRepositoryMock.findAll()).thenReturn(List.of(transactionEntity));
        when(transactionConverter.convert(transactionEntity)).thenReturn(Transaction.builder().id(1L).customerId(1L).propertyId(1L).build());
        GetAllTransactionResponse actual = transactionService.getAllTransactions();

        verify(transactionRepositoryMock).findAll();
        assertEquals(1, actual.getTransactions().size());
    }
    @Test
    void getTransactionsByCustomerId_ShouldReturnAllTransactionsByCustomerId() {
        TransactionEntity transactionEntity = TransactionEntity.builder().id(1L).customerId(1L).propertyId(1L).build();

        when(transactionRepositoryMock.findAllByCustomerId(1L)).thenReturn(List.of(transactionEntity));
        when(transactionConverter.convert(transactionEntity)).thenReturn(Transaction.builder().id(1L).customerId(1L).propertyId(1L).build());
        GetAllTransactionResponse actual = transactionService.getTransactionsByCustomerId(1L);

        verify(transactionRepositoryMock).findAllByCustomerId(1L);
        assertEquals(1, actual.getTransactions().size());
    }
    @Test
    void getTransactionsByPropertyId_ShouldReturnAllTransactionsByPropertyId() {
        TransactionEntity transactionEntity = TransactionEntity.builder().id(1L).customerId(1L).propertyId(1L).build();

        when(transactionRepositoryMock.findAllByPropertyId(1L)).thenReturn(List.of(transactionEntity));
        when(transactionConverter.convert(transactionEntity)).thenReturn(Transaction.builder().id(1L).customerId(1L).propertyId(1L).build());
        GetAllTransactionResponse actual = transactionService.getTransactionsByPropertyId(1L);

        verify(transactionRepositoryMock).findAllByPropertyId(1L);
        assertEquals(1, actual.getTransactions().size());
    }
}
