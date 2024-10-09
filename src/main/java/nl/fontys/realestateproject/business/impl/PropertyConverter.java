package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;

final class PropertyConverter {
    private PropertyConverter() {
    }

    public static Property convert(PropertyEntity propertyEntity) {
        return Property.builder()
                .id(propertyEntity.getId())
                .name(propertyEntity.getName())
                .description(propertyEntity.getDescription())
                .price(propertyEntity.getPrice())
                .propertyType(propertyEntity.getPropertyType())
                .listingType(propertyEntity.getListingType())
                .surfaceAreas(propertyEntity.getSurfaceAreas())
                .address(propertyEntity.getAddress())
                .build();
    }
}
