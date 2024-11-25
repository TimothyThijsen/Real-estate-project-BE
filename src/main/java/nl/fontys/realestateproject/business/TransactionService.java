package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.dto.transaction.GetAllTransactionResponse;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionResponse;

public interface TransactionService {
    MakeTransactionResponse makeTransaction(MakeTransactionRequest request);

    GetAllTransactionResponse getAllTransactions();

    GetAllTransactionResponse getTransactionsByCustomerId(Long customerId);

    GetAllTransactionResponse getTransactionsByPropertyId(Long propertyId);
}
