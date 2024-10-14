package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;

final class PropertySurfaceAreaConverter {
    public PropertySurfaceAreaConverter() {
    }

    public static PropertySurfaceArea convert(PropertySurfaceAreaEntity propertySurfaceAreaEntity) {
        return PropertySurfaceArea.builder()
                .nameOfSurfaceArea(propertySurfaceAreaEntity.getNameOfSurfaceArea())
                .areaInSquareMetre(propertySurfaceAreaEntity.getAreaInSquareMetre())
                .build();
    }
}
