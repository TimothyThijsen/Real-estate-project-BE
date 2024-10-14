package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.impl.PropertySurfaceAreaConverter;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;

final class PropertyConverter {
    public PropertyConverter() {
    }

    public static Property convert(PropertyEntity propertyEntity) {
        return Property.builder()
                .id(propertyEntity.getId())
                .description(propertyEntity.getDescription())
                .price(propertyEntity.getPrice())
                .propertyType(propertyEntity.getPropertyType())
                .listingType(propertyEntity.getListingType())
                .surfaceAreas(propertyEntity.getSurfaceAreas().stream()
                        .map(PropertySurfaceAreaConverter::convert)
                        .toList())
                .address(AddressConverter.convert(propertyEntity.getAddress()))
                .build();
    }


}

