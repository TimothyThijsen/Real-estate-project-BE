package nl.fontys.realestateproject.business.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
final class PropertyConverter {
    PropertySurfaceAreaConverter propertySurfaceAreaConverter;
    AddressConverter addressConverter;
    public Property convert(PropertyEntity propertyEntity) {
        return Property.builder()
                .id(propertyEntity.getId())
                .description(propertyEntity.getDescription())
                .price(propertyEntity.getPrice())
                .propertyType(propertyEntity.getPropertyType())
                .listingType(propertyEntity.getListingType())
                .surfaceAreas(propertyEntity.getSurfaceAreas().stream()
                        .map(propertySurfaceAreaConverter::convert)
                        .toList())
                .address(addressConverter.convert(propertyEntity.getAddress()))
                .build();
    }


}

