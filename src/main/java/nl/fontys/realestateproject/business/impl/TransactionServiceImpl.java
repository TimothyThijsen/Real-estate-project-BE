package nl.fontys.realestateproject.business.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.TransactionService;
import nl.fontys.realestateproject.business.dto.transaction.GetAllTransactionResponse;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionResponse;
import nl.fontys.realestateproject.business.exceptions.TransactionException;
import nl.fontys.realestateproject.domain.enums.ListingStatus;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.persistence.*;
import nl.fontys.realestateproject.persistence.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final ContractRepository contractRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Transactional
    @Override
    public MakeTransactionResponse makeTransaction(MakeTransactionRequest request) {
        PropertyEntity property = propertyRepository.getReferenceById(request.getPropertyId());
        AccountEntity customer = userRepository.getReferenceById(request.getCustomerId());
        ContractEntity contractEntity = ContractEntity.builder()
                .customer(customer)
                .property(property)
                .isActive(true)
                .startDate(LocalDateTime.now())
                .minimumContractEndDate(LocalDateTime.now().plusYears(1))
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .customerId(request.getCustomerId())
                .propertyId(request.getPropertyId())
                .date(LocalDateTime.now())
                .build();
        TransactionEntity savedTransaction;

        if (property.getListingType() == ListingType.SALE) {

            TransactionEntity existingTransaction = transactionRepository.findByPropertyId(request.getPropertyId());
            if(existingTransaction != null) {
                throw new TransactionException("Property is already sold");
            }
            property.setListingStatus(ListingStatus.SOLD.toString());
        } else {

            List<ContractEntity> existingContract = contractRepository.findByPropertyId(request.getPropertyId());
            if(existingContract != null && existingContract.stream().anyMatch(ContractEntity::isActive)) {
                throw new TransactionException("Property is already rented");
            }
            property.setListingStatus(ListingStatus.RENTED.toString());
        }
        RequestEntity requestEntity = requestRepository.findPendingPropertyByCustomerIdAndPropertyId(request.getCustomerId(), request.getPropertyId()).orElseThrow();
        requestEntity.setRequestStatus("ACCEPTED");

        try {
            contractRepository.save(contractEntity);
            savedTransaction = transactionRepository.save(transactionEntity);
            propertyRepository.save(property);
            requestRepository.save(requestEntity);

        } catch (Exception e) {
            throw new TransactionException();
        }

        requestRepository.cancelAllRequestsByPropertyId(request.getPropertyId());
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
