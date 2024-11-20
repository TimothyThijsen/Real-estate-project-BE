package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.TransactionService;
import nl.fontys.realestateproject.business.dto.transaction.GetAllTransactionResponse;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionResponse;
import nl.fontys.realestateproject.business.exceptions.TransactionException;
import nl.fontys.realestateproject.persistence.TransactionRepository;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;

    @Override
    public MakeTransactionResponse makeTransaction(MakeTransactionRequest request) {
         TransactionEntity transactionEntity = TransactionEntity.builder()
                .customerId(request.getCustomerId())
                .propertyId(request.getPropertyId())
                .date(LocalDateTime.now())
                .build();
        TransactionEntity savedTransaction;
        try {
            savedTransaction = transactionRepository.save(transactionEntity);

        } catch (Exception e) {
            throw new TransactionException();
        }
        return MakeTransactionResponse.builder().transactionId(savedTransaction.getId()).build();
    }

    @Override
    public GetAllTransactionResponse getAllTransactions() {
        List<TransactionEntity> results = transactionRepository.findAll();
        return convertToResponse(results);
    }

    @Override
    public GetAllTransactionResponse getTransactionsByCustomerId(Long customerId) {
        List<TransactionEntity> results = transactionRepository.findAllByCustomerId(customerId);
        return convertToResponse(results);
    }

    @Override
    public GetAllTransactionResponse getTransactionsByPropertyId(Long propertyId) {
        List<TransactionEntity> results = transactionRepository.findAllByPropertyId(propertyId);
        return convertToResponse(results);
    }

    private GetAllTransactionResponse convertToResponse(List<TransactionEntity> transactions) {
        return GetAllTransactionResponse.builder()
                .transactions(transactions.stream()
                        .map(transactionConverter::convert)
                        .toList())
                .build();
    }
}
