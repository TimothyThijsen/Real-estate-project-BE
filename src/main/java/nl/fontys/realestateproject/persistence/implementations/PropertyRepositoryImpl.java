package nl.fontys.realestateproject.persistence.implementations;

import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PropertyRepositoryImpl implements nl.fontys.realestateproject.persistence.PropertyRepository {
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
    public boolean UpdateProperty(PropertyEntity updatedProperty) {
        for (PropertyEntity property : savedProperties) {
            if (property.getId().equals(updatedProperty.getId())) {
                property = updatedProperty;
            }
        }
        return true;
    }

    @Override
    public boolean DeleteProperty(long propertyId) {
        return savedProperties.removeIf(propertyEntity -> propertyEntity.getId().equals(propertyId));
    }

    @Override
    public List<PropertyEntity> GetProperties() {
        return Collections.unmodifiableList(this.savedProperties);
    }
}
