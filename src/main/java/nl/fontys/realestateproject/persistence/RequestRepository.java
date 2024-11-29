package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByCustomerId(long userId);
    List<RequestEntity> findAllByPropertyId(long propertyId);
    @Query("SELECT r FROM RequestEntity r " +
            "LEFT JOIN PropertyEntity p on p.id = r.property.id " +
            "WHERE p.account.id = :agentId")
    List<RequestEntity> findAllByAgentId(long agentId);
}
