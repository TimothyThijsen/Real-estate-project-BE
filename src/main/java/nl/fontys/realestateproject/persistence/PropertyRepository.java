package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {


}
