package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByAccountId(long userId);
    List<RequestEntity> findAllByPropertyId(long propertyId);
    @Query("SELECT r FROM RequestEntity r " +
            "LEFT JOIN PropertyEntity p on p.id = r.property.id " +
            "WHERE p.account.id = :agentId")
    List<RequestEntity> findAllByAgentId(long agentId);
    @Query("SELECT r FROM RequestEntity r " +
            "WHERE r.property.id = :propertyId AND r.accountId = :customerId " +
            "AND r.requestStatus = 'PENDING'")
    RequestEntity findActivePropertyByCustomerIdAndPropertyId(long customerId, long propertyId);
}
