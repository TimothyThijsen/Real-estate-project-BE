package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    @Query("SELECT c FROM ContractEntity c " +
            "LEFT JOIN PropertyEntity p on p.id = c.propertyId " +
            "WHERE p.account.id = :agentId")
    List<ContractEntity> findAllByAgentId(long agentId);

    List<ContractEntity> findAllByCustomerId(long customerId);

    @Modifying
    @Query("UPDATE ContractEntity c SET c.isActive = false WHERE c.id = :id")
    void cancelContract(long id);

}
