package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
   /* @Query("SELECT p FROM PropertyEntity p left join  TransactionEntity t on p.id = t.propertyId where t.propertyId is null")
    List<PropertyEntity> findAllAvailableProperty();*/
    @Query("SELECT p FROM PropertyEntity p " +
            "left join  TransactionEntity t on p.id = t.propertyId " +
            "left join ContractEntity c on c.propertyId = p.id " +
            "WHERE (t.id is null) OR " +
            "(p.propertyType = 'RENTAL' AND (c.id IS NULL OR c.isActive = false))")
    List<PropertyEntity> findAllAvailableProperty();
    List<PropertyEntity> findAllByAccountId(long accountId);
    List<PropertyEntity> findAllByAddressCity(String city);
    List<PropertyEntity> findAllByAddressCountry(String country);

}
