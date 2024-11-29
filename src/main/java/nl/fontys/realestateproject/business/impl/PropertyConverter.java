package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.domain.enums.ListingStatus;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
final class PropertyConverter {
    private final AccountConverter accountConverter;
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
                .agent(accountConverter.convert(propertyEntity.getAccount()))
                .imageUrl(propertyEntity.getImageUrl())
                .listingStatus(ListingStatus.valueOf(propertyEntity.getListingStatus()))
                .build();
    }


}

