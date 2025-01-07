package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.dto.transaction.GetAllTransactionResponse;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionResponse;
import nl.fontys.realestateproject.business.exceptions.TransactionException;
import nl.fontys.realestateproject.domain.Transaction;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.persistence.*;
import nl.fontys.realestateproject.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    @Mock
    ContractRepository contractRepository;
    @Mock
    PropertyRepository propertyRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RequestRepository requestRepository;
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
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(ContractEntity.builder().build());
        when(propertyRepository.getReferenceById(1L)).thenReturn(new PropertyEntity().builder().id(1L).build());
        when(userRepository.getReferenceById(1L)).thenReturn(new AccountEntity().builder().id(1L).build());
        when(requestRepository.findPendingPropertyByCustomerIdAndPropertyId(1L, 1L)).thenReturn(Optional.of(RequestEntity.builder().build()));
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
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(ContractEntity.builder().build());
        when(propertyRepository.getReferenceById(1L)).thenReturn(new PropertyEntity().builder().id(1L).build());
        when(userRepository.getReferenceById(1L)).thenReturn(new AccountEntity().builder().id(1L).build());
        when(requestRepository.findPendingPropertyByCustomerIdAndPropertyId(1L, 1L)).thenReturn(Optional.of(RequestEntity.builder().build()));
        assertThrows(TransactionException.class, () -> transactionService.makeTransaction(request));
    }

    @Test
    void makeTransaction_ShouldSetPropertyListingStatusToSold() {
        MakeTransactionRequest request = MakeTransactionRequest.builder()
                .customerId(1L)
                .propertyId(1L)
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder().id(1L).build();
        when(transactionRepositoryMock.save(any(TransactionEntity.class))).thenReturn(transactionEntity);
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(ContractEntity.builder().build());
        PropertyEntity propertyEntity = new PropertyEntity().builder().id(1L).listingType(ListingType.SALE).build();
        when(userRepository.getReferenceById(1L)).thenReturn(new AccountEntity().builder().id(1L).build());
        when(requestRepository.findPendingPropertyByCustomerIdAndPropertyId(1L, 1L)).thenReturn(Optional.of(RequestEntity.builder().build()));
        when(propertyRepository.getReferenceById(1L)).thenReturn(propertyEntity);
        MakeTransactionResponse actual = transactionService.makeTransaction(request);

        verify(propertyRepository).save(propertyEntity);
        assertEquals(1, actual.getTransactionId());
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
