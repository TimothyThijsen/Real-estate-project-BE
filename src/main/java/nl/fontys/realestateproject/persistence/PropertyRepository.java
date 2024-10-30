package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {


}
