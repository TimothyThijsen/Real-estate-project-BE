package nl.fontys.realestateproject.persistence.implementations;

import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.IPropertyRepository;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PropertyRepository implements IPropertyRepository {
    private static long NEXT_ID = 1;
    private final List<PropertyEntity> savedProperties;

    public PropertyRepository() {
        this.savedProperties = new ArrayList<>();
    }
    @Override
    public Property GetProperty(int id) {
        return null;
    }

    @Override
    public PropertyEntity CreateProperty(PropertyEntity property) {
        if (property.getId() == null) {
            property.setId(NEXT_ID);
            NEXT_ID++;
            this.savedProperties.add(property);
        }
        return property;
    }

    @Override
    public void UpdateProperty(Property property) {

    }

    @Override
    public void DeleteProperty(Property property) {

    }

    @Override
    public List<PropertyEntity> GetProperties() {
        return Collections.unmodifiableList(this.savedProperties);
    }
}
