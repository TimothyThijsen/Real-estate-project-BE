package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertySurfaceAreaRepository extends JpaRepository<PropertySurfaceAreaEntity, Long> {


    void deleteAllByPropertyId(long id);
}
