package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertySurfaceAreaRepository extends JpaRepository<PropertySurfaceAreaEntity, Long> {
    Optional<PropertySurfaceAreaEntity> findByNameOfSurfaceAreaAndPropertyId(String nameOfSurfaceArea,long propertyId);

    void deleteAllByPropertyId(long id);
}
