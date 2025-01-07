package nl.fontys.realestateproject.business.impl.contract;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.impl.PropertyConverter;
import nl.fontys.realestateproject.business.impl.TransactionConverter;
import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.domain.Contract;
import nl.fontys.realestateproject.persistence.entity.ContractEntity;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@AllArgsConstructor
public final class ContractConverter {
    private final TransactionConverter transactionConverter;
    PropertyConverter propertyConverter;
    AccountConverter accountConverter;
    public Contract convert(ContractEntity contractEntity) {
        return Contract.builder()
                .id(contractEntity.getId())
                .active(contractEntity.isActive())
                .customer(accountConverter.convert(contractEntity.getCustomer()))
                .property(propertyConverter.convert(contractEntity.getProperty()))
                .startDate(contractEntity.getStartDate())
                .minimumContractEndDate(contractEntity.getMinimumContractEndDate())
                .transactions(contractEntity.getTransactions().stream()
                        .sorted(Comparator.comparing(TransactionEntity::getDate))
                        .map(transactionConverter::convert).toList())
                .build();
    }
}
