package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.domain.Property.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository {

    Optional<PropertyEntity> GetProperty(long id);

    PropertyEntity CreateProperty(PropertyEntity property);

    void UpdateProperty(Property property);

    boolean DeleteProperty(long propertyId);

    List<PropertyEntity> GetProperties();
}
