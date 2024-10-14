package nl.fontys.realestateproject.persistence.implementations;

import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
@Repository
public class PropertyRepositoryImpl  {
    private static long NEXT_ID = 1;
    private final List<PropertyEntity> savedProperties;

    public PropertyRepositoryImpl() {
        this.savedProperties = new ArrayList<>();
    }
    @Override
    public Optional<PropertyEntity> GetProperty(long propertyId) {

        return savedProperties.stream()
                .filter(propertyEntity -> propertyEntity.getId().equals(propertyId))
                .findFirst();
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
    public void UpdateProperty(PropertyEntity updatedProperty) {
        for (int i = 0; i < savedProperties.size(); i++) {
            if (savedProperties.get(i).getId().equals(updatedProperty.getId())) {
                savedProperties.set(i, updatedProperty);
                return;
            }
        }
    }

    @Override
    public void DeleteProperty(long propertyId) {
        savedProperties.removeIf(propertyEntity -> propertyEntity.getId().equals(propertyId));
    }

    @Override
    public List<PropertyEntity> GetProperties() {
        return Collections.unmodifiableList(this.savedProperties);
    }
}
*/
