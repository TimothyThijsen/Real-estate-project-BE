package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;


final class AddressConverter {

    public AddressConverter() {
    }

    public static Address convert(AddressEntity addressEntity) {
        return Address.builder()
                .street(addressEntity.getStreet())
                .city(addressEntity.getCity())
                .postalCode(addressEntity.getPostalCode())
                .country(addressEntity.getCountry())
                .build();
    }
}
