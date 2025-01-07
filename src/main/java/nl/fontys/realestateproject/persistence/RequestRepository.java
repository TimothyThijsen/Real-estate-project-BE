package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByAccountId(long userId);
    List<RequestEntity> findAllByPropertyId(long propertyId);

    @Query("SELECT r FROM RequestEntity r " +
            "LEFT JOIN PropertyEntity p on p.id = r.property.id " +
            "WHERE p.account.id = :agentId")
    List<RequestEntity> findAllByAgentId(long agentId);

    @Query("SELECT r FROM RequestEntity r " +
            "WHERE r.property.id = :propertyId AND r.account.id = :customerId " +
            "AND r.requestStatus = 'PENDING'")
    RequestEntity findActivePropertyByCustomerIdAndPropertyId(long customerId, long propertyId);

    @Query("SELECT r FROM RequestEntity r " +
            "WHERE r.property.id = :propertyId AND r.account.id = :customerId " +
            "AND r.requestStatus = 'PENDING'")
    Optional<RequestEntity> findPendingPropertyByCustomerIdAndPropertyId(long customerId, long propertyId);

    @Modifying
    @Query("UPDATE RequestEntity r SET r.requestStatus = 'DECLINED' WHERE r.property.id = :propertyId AND r.requestStatus = 'PENDING'")
    void cancelAllRequestsByPropertyId(long propertyId);
}
