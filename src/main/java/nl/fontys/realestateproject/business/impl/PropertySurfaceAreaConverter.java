package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.springframework.stereotype.Service;

@Service
final class PropertySurfaceAreaConverter {
    public PropertySurfaceArea convert(PropertySurfaceAreaEntity propertySurfaceAreaEntity) {
        return PropertySurfaceArea.builder()
                .nameOfSurfaceArea(propertySurfaceAreaEntity.getNameOfSurfaceArea())
                .areaInSquareMetre(propertySurfaceAreaEntity.getAreaInSquareMetre())
                .build();
    }
}
