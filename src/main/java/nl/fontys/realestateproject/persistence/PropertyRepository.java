package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.domain.DemandByRoomSize;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;


public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
    @Query("SELECT p FROM PropertyEntity p " +
            "left join  TransactionEntity t on p.id = t.propertyId " +
            "left join ContractEntity c on c.property = p " +
            "WHERE (t.id is null) OR " +
            "(p.listingType = 'RENTAL' AND (c.id IS NULL OR c.isActive = false))")
    List<PropertyEntity> findAllAvailableProperty();

    List<PropertyEntity> findAllByAccountId(long accountId);

    List<PropertyEntity> findAllByAddressCity(String city);

    List<PropertyEntity> findAllByAddressCountry(String country);

    @Query("SELECT p FROM PropertyEntity p " +
            "LEFT JOIN  TransactionEntity t on p.id = t.propertyId " +
            "LEFT JOIN ContractEntity c on c.property = p " +
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

    @Query("SELECT new nl.fontys.realestateproject.domain.DemandByRoomSize(subquery.totalSurfaceArea, COUNT(r.id)) " +
            "FROM (SELECT p.id AS propertyId, SUM(se.areaInSquareMetre) AS totalSurfaceArea " +
            "FROM PropertyEntity p " +
            "LEFT JOIN PropertySurfaceAreaEntity se ON se.property = p " +
            "WHERE p.account.id = :agentId " +
            "GROUP BY p.id)AS subquery " +
            "LEFT JOIN RequestEntity r ON r.property.id = subquery.propertyId " +
            "GROUP BY subquery.totalSurfaceArea " +
            "ORDER BY subquery.totalSurfaceArea ASC")
    List<DemandByRoomSize> getAllRoomSizeByDemand(long agentId);//include amount of property reserved by area size

    @Query("SELECT DISTINCT subquery.totalSurfaceArea " +
            "FROM (SELECT SUM(se.areaInSquareMetre) AS totalSurfaceArea " +
            "FROM PropertyEntity p " +
            "LEFT JOIN PropertySurfaceAreaEntity se ON se.property = p " +
            "WHERE p.account.id = :agentId " +
            "GROUP BY p.id) AS subquery " +
            "ORDER BY subquery.totalSurfaceArea ASC")
    List<Double> getRoomSizes(long agentId);

    @Query("SELECT COUNT(p.listingStatus) " +
            "FROM (SELECT p.id AS propertyId, SUM(se.areaInSquareMetre) AS totalSurfaceArea " +
            "FROM PropertyEntity p " +
            "LEFT JOIN PropertySurfaceAreaEntity se ON se.property = p " +
            "WHERE p.account.id = :agentId " +
            "GROUP BY p.id) AS subquery " +
            "LEFT JOIN PropertyEntity p ON p.id = subquery.propertyId " +
            "WHERE subquery.totalSurfaceArea = :roomSize " +
            "AND p.listingStatus = :listingStatus " +
            "GROUP BY p.listingStatus")
    Long getListingStatusByRoomSize(long agentId, Double roomSize, String listingStatus);
}
// OR p.address.city LIKE %:searchTerm% OR p.address.country LIKE %:searchTerm%