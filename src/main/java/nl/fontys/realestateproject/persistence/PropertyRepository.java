package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;


public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
    @Query("SELECT p FROM PropertyEntity p " +
            "left join  TransactionEntity t on p.id = t.propertyId " +
            "left join ContractEntity c on c.propertyId = p.id " +
            "WHERE (t.id is null) OR " +
            "(p.propertyType = 'RENTAL' AND (c.id IS NULL OR c.isActive = false))")
    List<PropertyEntity> findAllAvailableProperty();

    List<PropertyEntity> findAllByAccountId(long accountId);

    List<PropertyEntity> findAllByAddressCity(String city);

    List<PropertyEntity> findAllByAddressCountry(String country);

    @Query("SELECT p FROM PropertyEntity p " +
            "LEFT JOIN  TransactionEntity t on p.id = t.propertyId " +
            "LEFT JOIN ContractEntity c on c.propertyId = p.id " +
            "LEFT JOIN PropertySurfaceAreaEntity se on se.property = p " +
            "WHERE (p.listingStatus = 'ACTIVE') " +
            "AND p.listingType = :listingType " +
            "AND p.price >= :minPrice " +
            "AND (p.price <= :maxPrice or :maxPrice = 0) " +
            "AND (p.address.street LIKE %:searchTerm%) " +
            "GROUP BY p " +
            "HAVING SUM(se.areaInSquareMetre) >= :minSurfaceArea")
    Page<PropertyEntity> findAllByAvailableAndSearchTerm(ListingType listingType, Pageable pageable,
                                                         BigDecimal minPrice, BigDecimal maxPrice,
                                                         String searchTerm, Double minSurfaceArea);

}
// OR p.address.city LIKE %:searchTerm% OR p.address.country LIKE %:searchTerm%