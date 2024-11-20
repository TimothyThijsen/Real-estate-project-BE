package nl.fontys.realestateproject.business.impl.contract;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.impl.TransactionConverter;
import nl.fontys.realestateproject.domain.Contract;
import nl.fontys.realestateproject.persistence.entity.ContractEntity;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
@Service
@AllArgsConstructor
final class ContractConverter {
    private final TransactionConverter transactionConverter;
    public Contract convert(ContractEntity contractEntity) {
        return Contract.builder()
                .id(contractEntity.getId())
                .active(contractEntity.isActive())
                .customerId(contractEntity.getCustomerId())
                .propertyId(contractEntity.getPropertyId())
                .startDate(contractEntity.getStartDate())
                .minimumContractEndDate(contractEntity.getMinimumContractEndDate())
                .transactions(contractEntity.getTransactions().stream()
                        .sorted(Comparator.comparing(TransactionEntity::getDate))
                        .map(transactionConverter::convert).toList())
                .build();
    }
}
