package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Transaction;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Service;


@Service
final class TransactionConverter {
    public Transaction convert(TransactionEntity transactionEntity) {
        return Transaction.builder()
                .id(transactionEntity.getId())
                .propertyId(transactionEntity.getPropertyId())
                .customerId(transactionEntity.getCustomerId())
                .date(transactionEntity.getDate())
                .build();
    }
}
