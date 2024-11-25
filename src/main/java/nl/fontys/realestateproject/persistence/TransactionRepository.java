package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByCustomerId(long userId);

    List<TransactionEntity> findAllByPropertyId(long propertyId);
}
