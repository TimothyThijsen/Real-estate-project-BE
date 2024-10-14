package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
