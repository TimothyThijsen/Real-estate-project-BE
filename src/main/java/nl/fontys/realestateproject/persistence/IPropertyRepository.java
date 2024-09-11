package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;

import java.util.List;

public interface IPropertyRepository {

    Property GetProperty(int id);

    PropertyEntity CreateProperty(PropertyEntity property);

    void UpdateProperty(Property property);

    void DeleteProperty(Property property);

    List<PropertyEntity> GetProperties();
}
